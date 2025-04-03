package com.tuempresa.authservice.repository;

import com.tuempresa.authservice.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ActiveProfiles("test")
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void testSaveAndFindRole() {
        Role role = new Role();
        role.setName("ADMIN");
        role.setDescription("Rol de administrador");
        role.setPermissions(new HashSet<>());

        Role savedRole = roleRepository.save(role);
        assertNotNull(savedRole.getId());

        Optional<Role> found = roleRepository.findByName("ADMIN");
        assertTrue(found.isPresent());
        assertEquals("ADMIN", found.get().getName());
    }
} 