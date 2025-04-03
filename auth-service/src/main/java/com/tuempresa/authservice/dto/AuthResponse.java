package com.tuempresa.authservice.dto;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class AuthResponse {
    private String token;
    private String username;
    private String[] roles;
} 