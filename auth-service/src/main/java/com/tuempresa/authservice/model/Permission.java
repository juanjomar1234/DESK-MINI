package com.tuempresa.authservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@Document(collection = "permissions")
public class Permission {
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String name;
    
    private String description;
} 