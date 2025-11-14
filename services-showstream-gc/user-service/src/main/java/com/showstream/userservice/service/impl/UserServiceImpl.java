package com.showstream.userservice.service.impl;

import com.showstream.userservice.dto.AuthRequest;
import com.showstream.userservice.dto.AuthResponse;
import com.showstream.userservice.dto.UserRequestDTO;
import com.showstream.userservice.dto.UserResponseDTO;
import com.showstream.userservice.entity.Role;
import com.showstream.userservice.entity.User;
import com.showstream.userservice.exception.RoleNotFoundException;
import com.showstream.userservice.exception.UserAlreadyExistsException;
import com.showstream.userservice.repository.RoleRepository;
import com.showstream.userservice.repository.UserRepository;
import com.showstream.userservice.security.JwtTokenUtil;
import com.showstream.userservice.service.UserService;
import com.showstream.userservice.util.RoleName;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private final AuthenticationManager authManager ;
    private final JwtTokenUtil jwtTokenUtil;
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
                .firstName(requestDto.getFirstName())
                .lastName(requestDto.getLastName())
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
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
        return roleRepository.save(role);
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
                .roles(user.get().getRoles().stream()
                        .map(role -> role.getRoleName().toString()).collect(Collectors.toSet())
                )
                .build();
    }

    public AuthResponse login(AuthRequest user)
    {
        Authentication authentication =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName() , user.getPassword()));

        if(!authentication.isAuthenticated())
            throw  new UsernameNotFoundException("Invalid Credential") ;
        else
        {
            String token = jwtTokenUtil.generateToken(user.getUserName());
            return AuthResponse.builder()
                    .jwt(token)
                    .build();
        }
    }

    @Override
    public List<UserResponseDTO> getAllusers() {
        List<User> allUser = userRepository.findAll();

        List<UserResponseDTO> res = allUser.stream()
                .map(user -> UserResponseDTO.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .userName(user.getUserName())
                        .email(user.getEmail())
                         .roles(
                                 user.getRoles().stream()
                                         .map(role ->
                                                 role.getRoleName().toString()).collect(Collectors.toSet())
                                )
                        .build())
                .collect(Collectors.toList());
        return res;
    }

}
