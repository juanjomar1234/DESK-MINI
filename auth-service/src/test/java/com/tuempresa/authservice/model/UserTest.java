package com.tuempresa.authservice.model;

import org.junit.jupiter.api.Test;
import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    
    @Test
    void testUserCreation() {
        User user = new User();
        user.setId("1");
        user.setUsername("admin");
        user.setEmail("admin@example.com");
        user.setPassword("password");
        user.setRoles(new HashSet<>());
        
        assertEquals("1", user.getId());
        assertEquals("admin", user.getUsername());
        assertEquals("admin@example.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertTrue(user.isEnabled());
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertNotNull(user.getRoles());
    }
} 