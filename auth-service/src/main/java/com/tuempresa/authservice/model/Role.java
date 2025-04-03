package com.tuempresa.authservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Document(collection = "roles")
public class Role {
    @Id
    private String id;
    
    private String name;
    
    private String description;
    
    @DBRef
    private Set<Permission> permissions = new HashSet<>();
} 