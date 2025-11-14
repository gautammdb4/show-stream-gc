package com.showstream.userservice.dto;

import com.showstream.userservice.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String userName;
    private String email;



    //Added Field: A set of role names (e.g., ["ADMIN", "USER"])
    private Set<String> roles;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.firstName=user.getFirstName();
        this.lastName=user.getLastName();
        this.userName = user.getUserName();
        this.email = user.getEmail();

        // Map Set<Role> to Set<String> (role names)
        this.roles = user.getRoles().stream()
                .map(role -> role.getRoleName().name())
                .collect(Collectors.toSet());
    }
}
