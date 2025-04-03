package com.tuempresa.frontendservice.service;

import com.tuempresa.frontendservice.model.AppCard;
import com.tuempresa.frontendservice.repository.AppCardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppCardServiceTest {

    @Mock
    private AppCardRepository appCardRepository;

    @InjectMocks
    private AppCardService appCardService;

    private AppCard card;

    @BeforeEach
    void setUp() {
        card = new AppCard();
        card.setId("1");
        card.setName("Test App");
        card.setDescription("Test Description");
        card.setIconUrl("test-icon.png");
        card.setAppUrl("http://test-app.com");
        card.setOrder(1);
        card.setEnabled(true);
    }

    @Test
    void getAllEnabledCards_ReturnsOrderedList() {
        List<AppCard> cards = Arrays.asList(card);
        when(appCardRepository.findByEnabledTrueOrderByOrderAsc()).thenReturn(cards);

        List<AppCard> result = appCardService.getAllEnabledCards();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(card.getName(), result.get(0).getName());
    }

    @Test
    void createCard_Success() {
        when(appCardRepository.save(any(AppCard.class))).thenReturn(card);

        AppCard result = appCardService.createCard(card);

        assertNotNull(result);
        assertEquals(card.getName(), result.getName());
        verify(appCardRepository).save(any(AppCard.class));
    }
} 