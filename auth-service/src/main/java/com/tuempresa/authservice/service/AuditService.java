package com.tuempresa.authservice.service;

import com.tuempresa.authservice.model.AuditLog;
import com.tuempresa.authservice.repository.AuditLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditLogRepository auditLogRepository;
    private final HttpServletRequest request;

    public void logEvent(String username, String action, String details) {
        AuditLog log = AuditLog.builder()
            .username(username)
            .action(action)
            .details(details)
            .timestamp(LocalDateTime.now())
            .ipAddress(request.getRemoteAddr())
            .userAgent(request.getHeader("User-Agent"))
            .build();
        
        auditLogRepository.save(log);
    }
} 