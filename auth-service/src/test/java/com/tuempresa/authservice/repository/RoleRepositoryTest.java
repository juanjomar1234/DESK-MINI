package com.tuempresa.authservice.repository;

import com.tuempresa.authservice.model.Role;
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
class RoleRepositoryTest extends BaseTestConfig {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void findByName() {
        Role role = new Role();
        role.setName("ADMIN");
        role.setPermissions(new HashSet<>());
        roleRepository.save(role);

        Optional<Role> found = roleRepository.findByName("ADMIN");
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("ADMIN");
    }
} 