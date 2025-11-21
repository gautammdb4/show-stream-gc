package com.showstream.userservice.dto;

import com.showstream.userservice.util.RoleName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email ;

    @NotBlank(message = "First Name is required ")
    private String firstName ;

    private  String lastName ;

    private String userId ;

    @Size(min = 10, max = 10, message = "Phone number must be 10 digits")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid Indian phone number")
    private String phoneNumber;

    private LocalDate dateOfBirth;

    private String createdBy;

    private String updatedBy;

    private Boolean isActive ;

    @NotBlank(message = "Password is required")
    private String password ;

    private Set<RoleName> roles ;

}
