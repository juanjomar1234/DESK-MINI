package com.tuempresa.authservice.config;

import com.tuempresa.authservice.model.Permission;
import com.tuempresa.authservice.model.Role;
import com.tuempresa.authservice.model.User;
import com.tuempresa.authservice.repository.PermissionRepository;
import com.tuempresa.authservice.repository.RoleRepository;
import com.tuempresa.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "initial.owner")
public class InitialDataConfig implements CommandLineRunner {

    private String username;
    private String email;
    private String password;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            // Crear permisos básicos
            Permission readUsers = createPermission("READ_USERS", "Permite leer usuarios");
            Permission createUsers = createPermission("CREATE_USERS", "Permite crear usuarios");
            Permission updateUsers = createPermission("UPDATE_USERS", "Permite actualizar usuarios");
            Permission deleteUsers = createPermission("DELETE_USERS", "Permite eliminar usuarios");
            Permission manageRoles = createPermission("MANAGE_ROLES", "Permite gestionar roles");

            // Crear rol OWNER con todos los permisos
            Role ownerRole = new Role();
            ownerRole.setName("OWNER");
            ownerRole.setDescription("Rol con acceso total al sistema");
            ownerRole.setPermissions(new HashSet<>(Arrays.asList(
                readUsers, createUsers, updateUsers, deleteUsers, manageRoles
            )));
            roleRepository.save(ownerRole);

            // Crear usuario OWNER
            User ownerUser = new User();
            ownerUser.setUsername(username);
            ownerUser.setEmail(email);
            ownerUser.setPassword(passwordEncoder.encode(password));
            ownerUser.setEnabled(true);
            ownerUser.setPasswordChangeRequired(true);
            ownerUser.setRoles(Set.of(ownerRole));
            userRepository.save(ownerUser);
        }
    }

    private Permission createPermission(String name, String description) {
        Permission permission = new Permission();
        permission.setName(name);
        permission.setDescription(description);
        return permissionRepository.save(permission);
    }
} 