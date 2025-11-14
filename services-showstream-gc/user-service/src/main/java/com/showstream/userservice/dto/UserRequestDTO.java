package com.showstream.userservice.dto;

import com.showstream.userservice.util.RoleName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    @NotBlank(message = "Username is required")
    private String userName ;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email ;

    @NotBlank(message = "First Name is required ")
    private String firstName ;

    private  String lastName ;

    @NotBlank(message = "Password is required")
    private String password ;

    private Set<RoleName> roles ;

}
