package com.tuempresa.authservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.Set;

@Data
public class UserDto {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
    
    @NotBlank
    @Email
    private String email;
    
    private Set<String> roles;
    private boolean enabled = true;
} 