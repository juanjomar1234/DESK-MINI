package com.tuempresa.authservice.repository;

import com.tuempresa.authservice.model.AuditLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuditLogRepository extends MongoRepository<AuditLog, String> {
    // Los métodos básicos de CRUD son heredados de MongoRepository
} 