package com.showstream.userservice.service.impl;

import com.showstream.userservice.dto.UserRequestDTO;
import com.showstream.userservice.dto.UserResponseDTO;
import com.showstream.userservice.entity.Role;
import com.showstream.userservice.entity.User;
import com.showstream.userservice.exception.RoleNotFoundException;
import com.showstream.userservice.exception.UserAlreadyExistsException;
import com.showstream.userservice.repository.RoleRepository;
import com.showstream.userservice.repository.UserRepository;
import com.showstream.userservice.service.UserService;
import com.showstream.userservice.util.RoleName;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
//    private final PasswordEncoder passwordEncoder;

    /**
     * Registers a new user, validates for duplicates, and resolves roles.
     * The custom exceptions thrown here are caught by the GlobalExceptionHandler.
     */
    @Override
    @Transactional
    public UserResponseDTO registerUser(UserRequestDTO requestDto) {

        // check user is duplicate
        if (userRepository.findByUserName(requestDto.getUserName()).isPresent()) {
            throw new UserAlreadyExistsException(requestDto.getUserName());
        }
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(requestDto.getEmail());
        }

        // ROLE LOOKUP AND MAPPING
        Set<Role> roles = new HashSet<>();

        if (requestDto.getRoles() != null && !requestDto.getRoles().isEmpty()) {
            roles = requestDto.getRoles().stream()
                    .map(roleName -> roleRepository.findByRoleName(roleName)
                            .orElseThrow(() -> new RoleNotFoundException(roleName.name())))
                    .collect(Collectors.toSet());
        } else {
            // OPTIONAL: Default role assignment if no roles are provided (e.g., USER)
            // You must ensure RoleName.ROLE_USER exists in your enum and database.
            try {
                Role defaultRole = roleRepository.findByRoleName(RoleName.USER)
                        .orElseThrow(() -> new RoleNotFoundException("Default role USER"));
                roles.add(defaultRole);
            } catch (RoleNotFoundException e) {
                // Re-throw or handle if even the default role is missing
                throw new RoleNotFoundException("System setup error: Default role 'USER' is missing.");
            }
        }

        //Create and Encode Password
        User user = User.builder()
                .userName(requestDto.getUserName())
                .email(requestDto.getEmail())
//                .password(passwordEncoder.encode(requestDto.getPassword()))
                .password(requestDto.getPassword())
                .roles(roles)
                .build();

        // 4. Save to DB
        User savedUser = userRepository.save(user);

        // 5. Return safe DTO response
        return new UserResponseDTO(savedUser);
    }

    @Override
    public Role addingRole(String roleName) {
        Role role=Role.builder().roleName(RoleName.valueOf(roleName)).build();

        Role save = roleRepository.save(role);
        return save;
    }

    @Override
    public UserResponseDTO getUserDetails(String userName) throws Exception {

        Optional<User> user = userRepository.findByUserName(userName);

        if (!user.isPresent()) {
            throw new Exception("User not found "+userName);
        }


        return UserResponseDTO.builder()
                .email(user.get().getEmail())
                .userName(user.get().getUserName())
//                .roles(user.get().getRoles().stream().map(role -> ))
                .build();
    }
}
