package com.tuempresa.authservice.service;

import com.tuempresa.authservice.model.Permission;
import com.tuempresa.authservice.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final PermissionRepository permissionRepository;

    public Permission createPermission(Permission permission) {
        if (permissionRepository.existsByName(permission.getName())) {
            throw new IllegalArgumentException("Permission already exists");
        }
        return permissionRepository.save(permission);
    }

    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    public Optional<Permission> getPermissionByName(String name) {
        return permissionRepository.findByName(name);
    }

    public void deletePermission(String id) {
        permissionRepository.deleteById(id);
    }
} 