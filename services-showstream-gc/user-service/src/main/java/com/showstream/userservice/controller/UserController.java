package com.showstream.userservice.controller;


import com.showstream.userservice.dto.UserRequestDTO;
import com.showstream.userservice.dto.UserResponseDTO;
import com.showstream.userservice.entity.Role;
import com.showstream.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> createUser(
            @RequestBody @Valid  UserRequestDTO userDetails) throws Exception {

        return ResponseEntity.ok(userService.registerUser(userDetails));
    }

    @GetMapping("/details")
    public ResponseEntity<UserResponseDTO> getUserDetails( @PathVariable String userName) throws Exception {
        return ResponseEntity.ok(userService.getUserDetails(userName));
    }

    @GetMapping("/status")
    public ResponseEntity<String> status()
    {
        return ResponseEntity.ok("up and running ");
    }

    @PostMapping("/add-role")
    public ResponseEntity<Role> saveUserRole(
           @RequestParam String role)
    {
        return ResponseEntity.ok(userService.addingRole(role));
    }
}
