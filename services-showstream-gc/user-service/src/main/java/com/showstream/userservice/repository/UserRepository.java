package com.showstream.userservice.repository;

import com.showstream.userservice.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User , UUID> {
    @Override
    Optional<User> findById(UUID uuid);
    Optional<User> findByUserId(String userId) ;
    Optional<User> findByEmail(@NotBlank(message = "Email is required") @Email(message = "Email should be valid") String email);
}
