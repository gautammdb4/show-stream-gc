package com.showstream.userservice.service;

import com.showstream.userservice.dto.UserRequestDTO;
import com.showstream.userservice.dto.UserResponseDTO;
import com.showstream.userservice.entity.Role;

public interface UserService {

    // This method now handles the DTO to Entity mapping, password hashing, and role setting.
    UserResponseDTO registerUser(UserRequestDTO requestDto) throws Exception;

    Role addingRole(String role) ;

    UserResponseDTO getUserDetails(String userName) throws Exception;

}
