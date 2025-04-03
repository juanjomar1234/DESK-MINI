package com.tuempresa.authservice.config;

import com.tuempresa.authservice.model.Permission;
import com.tuempresa.authservice.model.Role;
import com.tuempresa.authservice.model.User;
import com.tuempresa.authservice.repository.PermissionRepository;
import com.tuempresa.authservice.repository.RoleRepository;
import com.tuempresa.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class InitialDataConfig {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            if (userRepository.count() > 0) {
                return;
            }

            // Crear permisos básicos
            Permission readPermission = new Permission();
            readPermission.setName("READ");
            readPermission.setDescription("Permiso de lectura");
            permissionRepository.save(readPermission);

            Permission writePermission = new Permission();
            writePermission.setName("WRITE");
            writePermission.setDescription("Permiso de escritura");
            permissionRepository.save(writePermission);

            // Crear rol de propietario con todos los permisos
            Role ownerRole = new Role();
            ownerRole.setName("OWNER");
            ownerRole.setDescription("Rol de propietario");
            ownerRole.setPermissions(new HashSet<>(Set.of(readPermission, writePermission)));
            roleRepository.save(ownerRole);

            // Crear usuario propietario
            User ownerUser = new User();
            ownerUser.setUsername("owner");
            ownerUser.setEmail("owner@example.com");
            ownerUser.setPassword(passwordEncoder.encode("password"));
            ownerUser.setEnabled(true);
            ownerUser.setPasswordChangeRequired(true);
            ownerUser.setRoles(Set.of(ownerRole));
            userRepository.save(ownerUser);
        };
    }
} 