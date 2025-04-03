package com.tuempresa.authservice.service;

import com.tuempresa.authservice.model.Permission;
import com.tuempresa.authservice.repository.PermissionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PermissionServiceTest {

    @Mock
    private PermissionRepository permissionRepository;

    @InjectMocks
    private PermissionService permissionService;

    private Permission permission;

    @BeforeEach
    void setUp() {
        permission = new Permission();
        permission.setId("1");
        permission.setName("READ_USERS");
        permission.setDescription("Permite leer usuarios");
    }

    @Test
    void createPermission_Success() {
        when(permissionRepository.existsByName(anyString())).thenReturn(false);
        when(permissionRepository.save(any(Permission.class))).thenReturn(permission);

        Permission created = permissionService.createPermission(permission);
        
        assertNotNull(created);
        assertEquals(permission.getName(), created.getName());
        verify(permissionRepository).save(any(Permission.class));
    }

    @Test
    void createPermission_DuplicateName() {
        when(permissionRepository.existsByName(anyString())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> 
            permissionService.createPermission(permission));
        
        verify(permissionRepository, never()).save(any(Permission.class));
    }

    @Test
    void getAllPermissions() {
        List<Permission> permissions = Arrays.asList(permission);
        when(permissionRepository.findAll()).thenReturn(permissions);

        List<Permission> result = permissionService.getAllPermissions();
        
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(permissionRepository).findAll();
    }

    @Test
    void getPermissionByName_Found() {
        when(permissionRepository.findByName(anyString())).thenReturn(Optional.of(permission));

        Optional<Permission> result = permissionService.getPermissionByName("READ_USERS");
        
        assertTrue(result.isPresent());
        assertEquals(permission.getName(), result.get().getName());
    }
} 