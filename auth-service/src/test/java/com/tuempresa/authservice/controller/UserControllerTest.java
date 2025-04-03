package com.tuempresa.authservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuempresa.authservice.dto.UserDto;
import com.tuempresa.authservice.model.User;
import com.tuempresa.authservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId("1");
        user.setUsername("testUser");
        user.setEmail("test@example.com");
        user.setEnabled(true);

        userDto = new UserDto();
        userDto.setUsername("testUser");
        userDto.setEmail("test@example.com");
        userDto.setEnabled(true);
    }

    @Test
    @WithMockUser(authorities = "READ_USERS")
    void getAllUsers_ReturnsUserList() throws Exception {
        when(userService.getAllUsers()).thenReturn(Arrays.asList(user));

        mockMvc.perform(get("/api/users"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].username").value(user.getUsername()))
            .andExpect(jsonPath("$[0].email").value(user.getEmail()));
    }

    @Test
    @WithMockUser(authorities = "READ_USERS")
    void getUserByUsername_UserExists_ReturnsUser() throws Exception {
        when(userService.getUserByUsername(anyString())).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/{username}", user.getUsername()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value(user.getUsername()))
            .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    @WithMockUser(authorities = "UPDATE_USERS")
    void updateUser_ValidUser_ReturnsUpdatedUser() throws Exception {
        when(userService.updateUser(anyString(), any(User.class))).thenReturn(user);

        mockMvc.perform(put("/api/users/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value(user.getUsername()))
            .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    @WithMockUser(authorities = "DELETE_USERS")
    void deleteUser_UserExists_ReturnsNoContent() throws Exception {
        doNothing().when(userService).deleteUser(anyString());

        mockMvc.perform(delete("/api/users/{id}", user.getId()))
            .andExpect(status().isNoContent());

        verify(userService).deleteUser(user.getId());
    }

    @Test
    @WithMockUser
    void getAllUsers_WithoutPermission_ReturnsForbidden() throws Exception {
        mockMvc.perform(get("/api/users"))
            .andExpect(status().isForbidden());
    }
} 