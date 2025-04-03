package com.tuempresa.authservice.service;

import com.tuempresa.authservice.model.AuditLog;
import com.tuempresa.authservice.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditService {
    private final AuditLogRepository auditLogRepository;

    public void logEvent(String username, String action, String details) {
        AuditLog log = AuditLog.builder()
            .username(username)
            .action(action)
            .details(details)
            .timestamp(LocalDateTime.now())
            .build();
        
        auditLogRepository.save(log);
    }
} 