package com.showstream.userservice.dto;

import com.showstream.userservice.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {


    private UUID id;
    private String firstName;
    private String lastName;
    private String userId;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;



    //Added Field: A set of role names (e.g., ["ADMIN", "USER"])
    private Set<String> roles;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.firstName=user.getFirstName();
        this.lastName=user.getLastName();
        this.email = user.getEmail();
        this.phoneNumber=user.getPhoneNum();
        this.dateOfBirth=user.getDateOfBirth();
        this.createdBy=user.getCreatedBy();
        this.updatedBy=user.getUpdatedBy();
        this.createdAt=user.getCreatedAt();
        this.updatedAt=user.getUpdatedAt();
        this.isActive=user.getIsActive();
        this.userId=user.getUserId();


        // Map Set<Role> to Set<String> (role names)
        this.roles = user.getRoles().stream()
                .map(role -> role.getRoleName().name())
                .collect(Collectors.toSet());
    }
}
