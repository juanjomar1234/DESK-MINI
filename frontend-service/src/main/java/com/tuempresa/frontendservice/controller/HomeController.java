package com.tuempresa.frontendservice.controller;

import com.tuempresa.frontendservice.service.AppCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final AppCardService appCardService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("cards", appCardService.getAllEnabledCards());
        return "home";
    }
} 