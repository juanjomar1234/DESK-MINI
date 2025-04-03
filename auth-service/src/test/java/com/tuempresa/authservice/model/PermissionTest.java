package com.tuempresa.authservice.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PermissionTest {
    
    @Test
    void testPermissionCreation() {
        Permission permission = new Permission();
        permission.setId("1");
        permission.setName("READ_USERS");
        permission.setDescription("Permite leer usuarios");
        
        assertEquals("1", permission.getId());
        assertEquals("READ_USERS", permission.getName());
        assertEquals("Permite leer usuarios", permission.getDescription());
    }
} 