package com.tuempresa.authservice.repository;

import com.tuempresa.authservice.model.Permission;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PermissionRepository extends MongoRepository<Permission, String> {
    Optional<Permission> findByName(String name);
    boolean existsByName(String name);
} 