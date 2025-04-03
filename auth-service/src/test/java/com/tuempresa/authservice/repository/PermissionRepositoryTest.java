package com.tuempresa.authservice.repository;

import com.tuempresa.authservice.model.Permission;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ActiveProfiles("test")
class PermissionRepositoryTest {

    @Autowired
    private PermissionRepository permissionRepository;

    @Test
    void testSaveAndFindPermission() {
        Permission permission = new Permission();
        permission.setName("READ_USERS");
        permission.setDescription("Permite leer usuarios");

        Permission savedPermission = permissionRepository.save(permission);
        assertNotNull(savedPermission.getId());

        Optional<Permission> found = permissionRepository.findByName("READ_USERS");
        assertTrue(found.isPresent());
        assertEquals("READ_USERS", found.get().getName());
    }
} 