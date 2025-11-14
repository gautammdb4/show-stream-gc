package com.showstream.userservice.controller;


import com.showstream.userservice.dto.AuthRequest;
import com.showstream.userservice.dto.AuthResponse;
import com.showstream.userservice.dto.UserRequestDTO;
import com.showstream.userservice.dto.UserResponseDTO;
import com.showstream.userservice.entity.Role;
import com.showstream.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/api")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity<UserResponseDTO> createUser(
            @RequestBody @Valid  UserRequestDTO userDetails) throws Exception {

        return ResponseEntity.ok(userService.registerUser(userDetails));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getUserDetails()  {
        return ResponseEntity.ok(userService.getAllusers());
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

    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest user) {
        return ResponseEntity.ok(userService.login(user));
    }
}
