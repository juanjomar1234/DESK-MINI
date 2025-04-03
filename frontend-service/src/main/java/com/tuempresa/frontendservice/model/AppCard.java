package com.tuempresa.frontendservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "app_cards")
public class AppCard {
    @Id
    private String id;
    private String name;
    private String description;
    private String iconUrl;
    private String appUrl;
    private int order;
    private boolean enabled = true;
} 