package com.tuempresa.authservice.repository;

import com.tuempresa.authservice.model.Permission;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ActiveProfiles("test")
class PermissionRepositoryTest extends BaseTestConfig {

    @Autowired
    private PermissionRepository permissionRepository;

    @Test
    void testSaveAndFindPermission() {
        Permission permission = new Permission();
        permission.setName("READ_USERS");
        permission.setDescription("Permite leer usuarios");

        Permission savedPermission = permissionRepository.save(permission);
        assertThat(savedPermission.getId()).isNotNull();

        Optional<Permission> found = permissionRepository.findByName("READ_USERS");
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("READ_USERS");
    }

    @Test
    void findByName() {
        Permission permission = new Permission();
        permission.setName("READ");
        permissionRepository.save(permission);

        Optional<Permission> found = permissionRepository.findByName("READ");
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("READ");
    }
} 