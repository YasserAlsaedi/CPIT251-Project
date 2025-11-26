package com.edprs.edprs_backend.controller;

import com.edprs.edprs_backend.model.user;
import com.edprs.edprs_backend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    // Constructor to inject the repository
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Endpoint to return the details of the currently logged-in user.
     * The unitCode is crucial for the mobile app's operation.
     */
    @GetMapping("/me")
    public ResponseEntity<user> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build(); // Not authenticated
        }
        
        // Find the user object in the database using the username from the security context
        return userRepository.findByUsername(userDetails.getUsername())
                .map(ResponseEntity::ok) // If found, return the user object (HTTP 200)
                .orElse(ResponseEntity.notFound().build()); // If somehow not found (HTTP 404)
    }
}