package com.showstream.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
}
