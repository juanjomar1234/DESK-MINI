package com.tuempresa.frontendservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthViewController.class)
class AuthViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void loginPage_ReturnsLoginView() throws Exception {
        mockMvc.perform(get("/auth/login"))
            .andExpect(status().isOk())
            .andExpect(view().name("login"))
            .andExpect(content().contentType("text/html;charset=UTF-8"));
    }

    @Test
    void registerPage_ReturnsRegisterView() throws Exception {
        mockMvc.perform(get("/auth/register"))
            .andExpect(status().isOk())
            .andExpect(view().name("register"))
            .andExpect(content().contentType("text/html;charset=UTF-8"));
    }
} 