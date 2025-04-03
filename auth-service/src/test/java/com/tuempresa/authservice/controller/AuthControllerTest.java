package com.tuempresa.authservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuempresa.authservice.dto.AuthRequest;
import com.tuempresa.authservice.dto.UserDto;
import com.tuempresa.authservice.model.User;
import com.tuempresa.authservice.security.JwtTokenProvider;
import com.tuempresa.authservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenProvider tokenProvider;

    @MockBean
    private UserService userService;

    private AuthRequest authRequest;
    private UserDto userDto;
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        authRequest = new AuthRequest();
        authRequest.setUsername("testUser");
        authRequest.setPassword("password");

        userDto = new UserDto();
        userDto.setUsername("testUser");
        userDto.setEmail("test@example.com");
        userDto.setPassword("password");

        authentication = new UsernamePasswordAuthenticationToken(
            "testUser",
            null,
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    @Test
    void login_ValidCredentials_ReturnsToken() throws Exception {
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(tokenProvider.generateToken(any())).thenReturn("test.jwt.token");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value("test.jwt.token"))
            .andExpect(jsonPath("$.username").value("testUser"));
    }

    @Test
    void register_ValidUser_ReturnsUserDto() throws Exception {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());

        when(userService.createUser(any())).thenReturn(user);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value(userDto.getUsername()))
            .andExpect(jsonPath("$.email").value(userDto.getEmail()));
    }

    @Test
    void register_InvalidUser_ReturnsBadRequest() throws Exception {
        userDto.setUsername("");  // Invalid username

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
            .andExpect(status().isBadRequest());
    }
} 