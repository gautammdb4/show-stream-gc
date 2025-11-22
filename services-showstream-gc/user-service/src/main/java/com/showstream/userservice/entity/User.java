package com.showstream.userservice.entity;

import com.showstream.userservice.model.enums.TokenClaims;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "users_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity{

    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private String firstName ;
    @Column
    private String lastName ;

    @Column(unique = true)
    private String userId;

    @Column(unique = true,nullable = false)
    private String email;

    private  String password;

    @Column
    private String phoneNum;

    @Column( name = "date_of_birth")
    private LocalDate dateOfBirth;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role>  roles = new HashSet<>();

    @Column
    private Boolean isActive;

    public Map<String, Object> getClaims() {

        final Map<String, Object> claims = new HashMap<>();

        claims.put(TokenClaims.USER_ID.getValue(), this.id);
        claims.put(
                TokenClaims.USER_ROLE.getValue(),
                this.roles.stream()
                        .map(role -> role.getRoleName().name())
                        .collect(Collectors.toList())
        );
        claims.put(TokenClaims.USER_STATUS.getValue(), this.isActive);
        claims.put(TokenClaims.USER_FIRST_NAME.getValue(), this.firstName);
        claims.put(TokenClaims.USER_LAST_NAME.getValue(), this.lastName);
        claims.put(TokenClaims.USER_EMAIL.getValue(), this.email);
        claims.put(TokenClaims.USER_PHONE_NUMBER.getValue(), this.phoneNum);

        return claims;

    }
}
