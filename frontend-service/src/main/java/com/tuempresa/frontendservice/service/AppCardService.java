package com.tuempresa.frontendservice.service;

import com.tuempresa.frontendservice.model.AppCard;
import com.tuempresa.frontendservice.repository.AppCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppCardService {
    
    private final AppCardRepository appCardRepository;
    
    public List<AppCard> getAllEnabledCards() {
        return appCardRepository.findByEnabledTrueOrderByOrderAsc();
    }
    
    public AppCard createCard(AppCard card) {
        return appCardRepository.save(card);
    }
    
    public void deleteCard(String id) {
        appCardRepository.deleteById(id);
    }
    
    public AppCard updateCard(String id, AppCard card) {
        AppCard existingCard = appCardRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Card not found"));
        
        existingCard.setName(card.getName());
        existingCard.setDescription(card.getDescription());
        existingCard.setIconUrl(card.getIconUrl());
        existingCard.setAppUrl(card.getAppUrl());
        existingCard.setOrder(card.getOrder());
        existingCard.setEnabled(card.isEnabled());
        
        return appCardRepository.save(existingCard);
    }
} 