package com.tuempresa.authservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import java.util.Set;

@Data
@Document(collection = "roles")
public class Role {
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String name;
    
    private String description;
    
    @DBRef
    private Set<Permission> permissions;
} 