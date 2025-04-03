package com.tuempresa.authservice.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtTokenProvider tokenProvider;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        userDetails = new User("testUser", "password", Collections.emptyList());
    }

    @Test
    void doFilterInternal_ValidToken_SetsAuthentication() throws ServletException, IOException {
        String token = "valid.jwt.token";
        request.addHeader("Authorization", "Bearer " + token);

        when(tokenProvider.validateToken(token)).thenReturn(true);
        when(tokenProvider.getUsernameFromToken(token)).thenReturn("testUser");
        when(userDetailsService.loadUserByUsername("testUser")).thenReturn(userDetails);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verify(userDetailsService).loadUserByUsername("testUser");
    }

    @Test
    void doFilterInternal_InvalidToken_ContinuesChain() throws ServletException, IOException {
        String token = "invalid.jwt.token";
        request.addHeader("Authorization", "Bearer " + token);

        when(tokenProvider.validateToken(token)).thenReturn(false);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verify(userDetailsService, never()).loadUserByUsername(anyString());
    }

    @Test
    void doFilterInternal_NoToken_ContinuesChain() throws ServletException, IOException {
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verify(tokenProvider, never()).validateToken(anyString());
        verify(userDetailsService, never()).loadUserByUsername(anyString());
    }
} 