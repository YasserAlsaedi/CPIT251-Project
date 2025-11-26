package com.edprs.edprs_backend.controller;

import com.edprs.edprs_backend.model.user;
import com.edprs.edprs_backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/debug")
public class DebugController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DebugController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 1. LIST ALL USERS (To see if DataSeeder actually ran)
    @GetMapping("/users")
    public List<user> listAllUsers() {
        return userRepository.findAll();
    }

    // 2. TEST PASSWORD (To see if "123" actually matches)
    @GetMapping("/test")
    public String testPassword(@RequestParam String username, @RequestParam String rawPassword) {
        user u = userRepository.findByUsername(username).orElse(null);
        if (u == null) return "❌ User '" + username + "' DOES NOT EXIST in database.";
        
        boolean matches = passwordEncoder.matches(rawPassword, u.getPassword());
        
        return matches 
            ? "✅ SUCCESS! Password matches." 
            : "❌ FAILED. Database has hash: " + u.getPassword() + " but it does not match '" + rawPassword + "'";
    }
}