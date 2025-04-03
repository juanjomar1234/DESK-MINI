package com.tuempresa.authservice.model;

import org.junit.jupiter.api.Test;
import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.*;

class RoleTest {
    
    @Test
    void testRoleCreation() {
        Role role = new Role();
        role.setId("1");
        role.setName("ADMIN");
        role.setDescription("Rol de administrador");
        role.setPermissions(new HashSet<>());
        
        assertEquals("1", role.getId());
        assertEquals("ADMIN", role.getName());
        assertEquals("Rol de administrador", role.getDescription());
        assertNotNull(role.getPermissions());
    }
} 