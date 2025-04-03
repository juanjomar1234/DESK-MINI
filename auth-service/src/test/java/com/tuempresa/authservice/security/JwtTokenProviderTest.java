package com.tuempresa.authservice.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    private JwtTokenProvider tokenProvider;

    @BeforeEach
    void setUp() {
        tokenProvider = new JwtTokenProvider();
        ReflectionTestUtils.setField(tokenProvider, "jwtSecret", 
            "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970");
        ReflectionTestUtils.setField(tokenProvider, "jwtExpirationInMs", 86400000L);
        tokenProvider.init();
    }

    @Test
    void generateToken_ValidAuthentication_ReturnsToken() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            "testUser",
            null,
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );

        String token = tokenProvider.generateToken(authentication);

        assertNotNull(token);
        assertTrue(tokenProvider.validateToken(token));
        assertEquals("testUser", tokenProvider.getUsernameFromToken(token));
    }

    @Test
    void validateToken_InvalidToken_ReturnsFalse() {
        String invalidToken = "invalid.token.here";
        assertFalse(tokenProvider.validateToken(invalidToken));
    }

    @Test
    void validateToken_ExpiredToken_ReturnsFalse() {
        ReflectionTestUtils.setField(tokenProvider, "jwtExpirationInMs", -86400000L);
        
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            "testUser",
            null,
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );

        String token = tokenProvider.generateToken(authentication);
        assertFalse(tokenProvider.validateToken(token));
    }
} 