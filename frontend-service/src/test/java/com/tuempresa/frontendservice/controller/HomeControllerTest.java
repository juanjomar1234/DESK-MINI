package com.tuempresa.frontendservice.controller;

import com.tuempresa.frontendservice.model.AppCard;
import com.tuempresa.frontendservice.service.AppCardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomeController.class)
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppCardService appCardService;

    @Test
    void home_ReturnsHomePageWithCards() throws Exception {
        AppCard card = new AppCard();
        card.setName("Test App");
        card.setDescription("Test Description");

        when(appCardService.getAllEnabledCards()).thenReturn(Arrays.asList(card));

        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(view().name("home"))
            .andExpect(model().attributeExists("cards"));
    }
} 