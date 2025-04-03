package com.tuempresa.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Data
public class UserDto {
    @NotBlank(message = "Username is required")
    private String username;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    private String password;
    private Set<String> roles;
    private boolean enabled = true;
} 