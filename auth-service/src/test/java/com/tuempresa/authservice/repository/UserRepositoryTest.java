package com.tuempresa.authservice.repository;

import com.tuempresa.authservice.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import com.tuempresa.authservice.config.BaseTestConfig;

@DataMongoTest
@ActiveProfiles("test")
class UserRepositoryTest extends BaseTestConfig {

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
        assertThat(savedUser.getId()).isNotNull();

        Optional<User> foundByUsername = userRepository.findByUsername("admin");
        assertThat(foundByUsername).isPresent();
        assertThat(foundByUsername.get().getUsername()).isEqualTo("admin");

        Optional<User> foundByEmail = userRepository.findByEmail("admin@example.com");
        assertThat(foundByEmail).isPresent();
        assertThat(foundByEmail.get().getEmail()).isEqualTo("admin@example.com");
    }

    @Test
    void findByUsername() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRoles(new HashSet<>());
        userRepository.save(user);

        Optional<User> found = userRepository.findByUsername("testuser");
        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("testuser");

        Optional<User> notFound = userRepository.findByUsername("nonexistent");
        assertThat(notFound).isEmpty();
    }
} 