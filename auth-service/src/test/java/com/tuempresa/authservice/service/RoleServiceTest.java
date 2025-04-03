package com.tuempresa.authservice.service;

import com.tuempresa.authservice.model.Role;
import com.tuempresa.authservice.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role();
        role.setId("1");
        role.setName("ADMIN");
        role.setDescription("Rol de administrador");
        role.setPermissions(new HashSet<>());
    }

    @Test
    void createRole_Success() {
        when(roleRepository.existsByName(anyString())).thenReturn(false);
        when(roleRepository.save(any(Role.class))).thenReturn(role);

        Role created = roleService.createRole(role);
        
        assertNotNull(created);
        assertEquals(role.getName(), created.getName());
        verify(roleRepository).save(any(Role.class));
    }

    @Test
    void createRole_DuplicateName() {
        when(roleRepository.existsByName(anyString())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> 
            roleService.createRole(role));
        
        verify(roleRepository, never()).save(any(Role.class));
    }

    @Test
    void updateRole_Success() {
        when(roleRepository.findById(anyString())).thenReturn(Optional.of(role));
        when(roleRepository.save(any(Role.class))).thenReturn(role);

        Role updated = roleService.updateRole("1", role);
        
        assertNotNull(updated);
        assertEquals(role.getName(), updated.getName());
        verify(roleRepository).save(any(Role.class));
    }

    @Test
    void updateRole_NotFound() {
        when(roleRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> 
            roleService.updateRole("1", role));
        
        verify(roleRepository, never()).save(any(Role.class));
    }
} 