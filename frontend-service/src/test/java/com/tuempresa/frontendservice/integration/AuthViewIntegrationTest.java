package com.tuempresa.frontendservice.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthViewIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void loginPage_ContainsExpectedElements() throws Exception {
        mockMvc.perform(get("/auth/login"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Login")))
            .andExpect(content().string(containsString("Username")))
            .andExpect(content().string(containsString("Password")))
            .andExpect(content().string(containsString("Sign in")))
            .andExpect(content().string(containsString("Don't have an account?")))
            .andExpect(content().string(containsString("Register here")));
    }

    @Test
    void registerPage_ContainsExpectedElements() throws Exception {
        mockMvc.perform(get("/auth/register"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Create Account")))
            .andExpect(content().string(containsString("Username")))
            .andExpect(content().string(containsString("Email")))
            .andExpect(content().string(containsString("Password")))
            .andExpect(content().string(containsString("Confirm Password")))
            .andExpect(content().string(containsString("Already have an account?")))
            .andExpect(content().string(containsString("Login here")));
    }

    @Test
    void loginForm_HasRequiredAttributes() throws Exception {
        mockMvc.perform(get("/auth/login"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("form")))
            .andExpect(content().string(containsString("input")))
            .andExpect(content().string(containsString("required")))
            .andExpect(content().string(containsString("type=\"submit\"")));
    }

    @Test
    void registerForm_HasRequiredAttributes() throws Exception {
        mockMvc.perform(get("/auth/register"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("form")))
            .andExpect(content().string(containsString("input")))
            .andExpect(content().string(containsString("required")))
            .andExpect(content().string(containsString("type=\"submit\"")));
    }
} 