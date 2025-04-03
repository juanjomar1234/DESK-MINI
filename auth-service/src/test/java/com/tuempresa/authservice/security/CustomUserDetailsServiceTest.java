package com.tuempresa.authservice.security;

import com.tuempresa.authservice.model.Permission;
import com.tuempresa.authservice.model.Role;
import com.tuempresa.authservice.model.User;
import com.tuempresa.authservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    private User user;
    private Role role;
    private Permission permission;

    @BeforeEach
    void setUp() {
        permission = new Permission();
        permission.setName("READ_USERS");

        role = new Role();
        role.setName("ADMIN");
        role.setPermissions(Set.of(permission));

        user = new User();
        user.setUsername("admin");
        user.setPassword("password");
        user.setEnabled(true);
        user.setRoles(Set.of(role));
    }

    @Test
    void loadUserByUsername_UserExists_ReturnsUserDetails() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername("admin");

        assertNotNull(userDetails);
        assertEquals(user.getUsername(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertTrue(userDetails.isEnabled());
        assertFalse(userDetails.getAuthorities().isEmpty());
    }

    @Test
    void loadUserByUsername_UserNotFound_ThrowsException() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> 
            userDetailsService.loadUserByUsername("unknown"));
    }
} 