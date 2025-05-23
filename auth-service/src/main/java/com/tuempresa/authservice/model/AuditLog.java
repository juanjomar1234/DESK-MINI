package com.tuempresa.authservice.model;

import lombok.Data;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "audit_logs")
public class AuditLog {
    @Id
    private String id;
    
    private String username;
    
    private String action;
    
    private String details;
    
    private LocalDateTime timestamp;
} 