package com.tuempresa.authservice.service;

import com.tuempresa.authservice.model.Role;
import com.tuempresa.authservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role createRole(Role role) {
        if (roleRepository.existsByName(role.getName())) {
            throw new IllegalArgumentException("Role already exists");
        }
        return roleRepository.save(role);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Optional<Role> getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    public void deleteRole(String id) {
        roleRepository.deleteById(id);
    }

    public Role updateRole(String id, Role role) {
        Role existingRole = roleRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Role not found"));
        
        existingRole.setName(role.getName());
        existingRole.setDescription(role.getDescription());
        existingRole.setPermissions(role.getPermissions());
        
        return roleRepository.save(existingRole);
    }
} 