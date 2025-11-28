package com.edprs.edprs_backend.repository;

import com.edprs.edprs_backend.model.user;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<user, Long> {
    // Return Optional<User> to prevent NullPointerExceptions
    Optional<user> findByUsername(String username);
}