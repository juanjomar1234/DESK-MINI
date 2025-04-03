package com.tuempresa.authservice.repository;

import com.tuempresa.authservice.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveAndFindUser() {
        User user = new User();
        user.setUsername("admin");
        user.setEmail("admin@example.com");
        user.setPassword("password");
        user.setRoles(new HashSet<>());

        User savedUser = userRepository.save(user);
        assertNotNull(savedUser.getId());

        Optional<User> foundByUsername = userRepository.findByUsername("admin");
        assertTrue(foundByUsername.isPresent());
        assertEquals("admin", foundByUsername.get().getUsername());

        Optional<User> foundByEmail = userRepository.findByEmail("admin@example.com");
        assertTrue(foundByEmail.isPresent());
        assertEquals("admin@example.com", foundByEmail.get().getEmail());
    }
} 