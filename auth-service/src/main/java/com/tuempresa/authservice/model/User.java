package com.tuempresa.authservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import java.util.Set;
import java.util.Date;
import java.util.HashSet;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String username;
    
    @Indexed(unique = true)
    private String email;
    
    private String password;
    
    private boolean enabled = true;
    
    private boolean accountNonExpired = true;
    
    private boolean credentialsNonExpired = true;
    
    private boolean accountNonLocked = true;
    
    @DBRef
    private Set<Role> roles = new HashSet<>();

    private boolean passwordChangeRequired = false;
    private Date lastPasswordChangeDate;
} 