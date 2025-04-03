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
class NavigationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void navigation_BetweenLoginAndRegister() throws Exception {
        // Verificar link a registro desde login
        mockMvc.perform(get("/auth/login"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("href=\"/register\"")));

        // Verificar link a login desde registro
        mockMvc.perform(get("/auth/register"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("href=\"/login\"")));
    }

    @Test
    void styling_ElementsHaveCorrectClasses() throws Exception {
        mockMvc.perform(get("/auth/login"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("class=\"auth-card")))
            .andExpect(content().string(containsString("class=\"form-input")))
            .andExpect(content().string(containsString("class=\"btn-primary")));
    }
} 