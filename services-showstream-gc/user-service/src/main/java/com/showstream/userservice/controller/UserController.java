package com.showstream.userservice.controller;


import com.showstream.userservice.dto.AuthRequest;
import com.showstream.userservice.dto.CustomResponse;
import com.showstream.userservice.dto.TokenResponse;
import com.showstream.userservice.dto.UserRequestDTO;
import com.showstream.userservice.dto.UserResponseDTO;
import com.showstream.userservice.entity.Role;
import com.showstream.userservice.model.Token;
import com.showstream.userservice.service.TokenService;
import com.showstream.userservice.service.UserLoginService;
import com.showstream.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/api/users")
@AllArgsConstructor
@Slf4j
public class UserController {

    private UserService userService;
    private final UserLoginService userLoginService;
    private final TokenService tokenService;


    // register user details
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> createUser(
            @RequestBody @Valid  UserRequestDTO userDetails) throws Exception {

        return ResponseEntity.ok(userService.registerUser(userDetails));
    }

    // fetch all user details
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getUserDetails()  {
        return ResponseEntity.ok(userService.getAllusers());
    }

    //login user
    @PostMapping("/login")
    public CustomResponse<TokenResponse> loginUser(@RequestBody @Valid final AuthRequest loginRequest) {
        log.info("UserController | login");
        final Token token = userLoginService.login(loginRequest);
        return CustomResponse.successOf(new TokenResponse(token));
    }

    //fetch user details by userId or email
    @GetMapping("/user/email")
    public ResponseEntity<UserResponseDTO> getUserDetailsByEmail(@RequestParam String email) throws Exception {
        return ResponseEntity.ok(userService.getUserDetails(email));
    }


    @PostMapping("/validate-token")
    public ResponseEntity<Void> validateToken(@RequestParam String token) {
        log.info("UserController | validateToken");
        tokenService.verifyAndValidate(token);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/status")
    public ResponseEntity<String> status()
    {
        return ResponseEntity.ok("up and running ");
    }

    @PostMapping("/role")
    public ResponseEntity<Role> saveUserRole(
           @RequestParam String role)
    {
        return ResponseEntity.ok(userService.addingRole(role));
    }


}
