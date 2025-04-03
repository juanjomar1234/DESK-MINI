package com.tuempresa.authservice.service;

import com.tuempresa.authservice.model.User;
import com.tuempresa.authservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId("1");
        user.setUsername("admin");
        user.setEmail("admin@example.com");
        user.setPassword("password");
        user.setRoles(new HashSet<>());
    }

    @Test
    void createUser_Success() {
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User created = userService.createUser(user);
        
        assertNotNull(created);
        assertEquals(user.getUsername(), created.getUsername());
        verify(passwordEncoder).encode(anyString());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_DuplicateUsername() {
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> 
            userService.createUser(user));
        
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void updateUser_Success() {
        User existingUser = new User();
        existingUser.setId("1");
        existingUser.setUsername("oldUsername");
        existingUser.setEmail("old@example.com");

        when(userRepository.findById(anyString())).thenReturn(Optional.of(existingUser));
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updated = userService.updateUser("1", user);
        
        assertNotNull(updated);
        assertEquals(user.getUsername(), updated.getUsername());
        assertEquals(user.getEmail(), updated.getEmail());
    }

    @Test
    void updateUser_NotFound() {
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> 
            userService.updateUser("1", user));
        
        verify(userRepository, never()).save(any(User.class));
    }
} 