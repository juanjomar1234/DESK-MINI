package com.tuempresa.frontendservice.repository;

import com.tuempresa.frontendservice.model.AppCard;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AppCardRepository extends MongoRepository<AppCard, String> {
    List<AppCard> findByEnabledTrueOrderByOrderAsc();
} 