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
import com.showstream.userservice.model.enums.RoleName;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenServiceImpl tokenService;


    /**
     * Registers a new user, validates for duplicates, and resolves roles.
     * The custom exceptions thrown here are caught by the GlobalExceptionHandler.
     */
    @Override
    @Transactional
    public UserResponseDTO registerUser(UserRequestDTO requestDto) {

        // check user is duplicate
        if (userRepository.findByUserId(requestDto.getUserId()).isPresent()) {
            throw new UserAlreadyExistsException(requestDto.getUserId());
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
                .firstName(requestDto.getFirstName())
                .lastName(requestDto.getLastName())
                .userId(requestDto.getUserId())
                .email(requestDto.getEmail())
                .phoneNum(requestDto.getPhoneNumber())
                .dateOfBirth(requestDto.getDateOfBirth())
                .isActive(requestDto.getIsActive())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .roles(roles)
                .build();

        user.setCreatedBy(requestDto.getCreatedBy());
        user.setUpdatedBy(requestDto.getUpdatedBy());

        // 4. Save to DB
        User savedUser = userRepository.save(user);

        // 5. Return safe DTO response
        return new UserResponseDTO(savedUser);
    }

    @Override
    public Role addingRole(String roleName) {
        Role role=Role.builder().roleName(RoleName.valueOf(roleName)).build();
        return roleRepository.save(role);
    }

    @Override
    public UserResponseDTO getUserDetails(String email) throws Exception {

        Optional<User> user = userRepository.findByEmail(email);

        if (!user.isPresent()) {
            throw new Exception("User not found "+email);
        }
        return new UserResponseDTO(user.get());
    }

    @Override
    public UserResponseDTO getUserDetailsByUserID(String userID) {
        Optional<User> user = userRepository.findByUserId(userID);

        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found "+userID);
        }
        return new UserResponseDTO(user.get());
    }

    @Override
    public List<UserResponseDTO> getAllusers() {
        List<User> allUser = userRepository.findAll();

        List<UserResponseDTO> res = allUser.stream()
                .map(user -> new UserResponseDTO(user))
                .collect(Collectors.toList());

        return res;
    }

}
