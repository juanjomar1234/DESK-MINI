package com.tuempresa.authservice.service;

import com.tuempresa.authservice.model.User;
import com.tuempresa.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public User updateUser(String id, User user) {
        User existingUser = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        if (!existingUser.getUsername().equals(user.getUsername()) && 
            userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        
        if (!existingUser.getEmail().equals(user.getEmail()) && 
            userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        existingUser.setEnabled(user.isEnabled());
        existingUser.setRoles(user.getRoles());
        
        return userRepository.save(existingUser);
    }
} 