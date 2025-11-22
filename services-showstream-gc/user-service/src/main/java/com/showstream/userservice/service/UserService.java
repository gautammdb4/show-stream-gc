package com.showstream.userservice.service;

import com.showstream.userservice.dto.UserRequestDTO;
import com.showstream.userservice.dto.UserResponseDTO;
import com.showstream.userservice.entity.Role;

import java.util.List;

public interface UserService {

    UserResponseDTO registerUser(UserRequestDTO requestDto) throws Exception;
    Role addingRole(String role) ;
    UserResponseDTO getUserDetails(String email) throws Exception;
    UserResponseDTO getUserDetailsByUserID(String userID) ;
    List<UserResponseDTO> getAllusers();
}
