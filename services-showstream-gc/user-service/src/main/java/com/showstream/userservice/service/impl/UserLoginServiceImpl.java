package com.showstream.userservice.service.impl;

import com.showstream.userservice.dto.AuthRequest;
import com.showstream.userservice.entity.User;
import com.showstream.userservice.exception.PasswordNotValidException;
import com.showstream.userservice.exception.UserNotFoundException;
import com.showstream.userservice.model.Token;
import com.showstream.userservice.repository.UserRepository;
import com.showstream.userservice.service.TokenService;
import com.showstream.userservice.service.UserLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link UserLoginService} for handling user login operations.
 * This service handles user authentication by validating login credentials and generating JWT tokens.
 */
@Service
@RequiredArgsConstructor
public class UserLoginServiceImpl implements UserLoginService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenService tokenService;

    /**
     * Authenticates a user based on the provided login request and generates a JWT token upon successful login.
     * This method retrieves the user entity from the database using the email provided in the login request.
     * It then validates the provided password against the stored password. If the credentials are valid,
     * it generates and returns a JWT token containing the user's claims.
     *
     * @param loginRequest the {@link AuthRequest} object containing the user's email and password.
     * @return a {@link Token} object containing the generated JWT token.
     * @throws UserNotFoundException if no user is found with the given email.
     * @throws PasswordNotValidException if the provided password does not match the stored password.
     */
    @Override
    public Token login(AuthRequest loginRequest) {

        final User userEntityFromDB = userRepository
                .findByEmail(loginRequest.getEmail())
                .orElseThrow(
                        () -> new UserNotFoundException("Can't find with given email: "
                                + loginRequest.getEmail())
                );

        if (Boolean.FALSE.equals(passwordEncoder.matches(
                loginRequest.getPassword(), userEntityFromDB.getPassword()))) {
            throw new PasswordNotValidException();
        }

        return tokenService.generateToken(userEntityFromDB.getClaims());

    }

}
