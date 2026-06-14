package com.example.taxi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class homecontroller {

    @GetMapping("/")
    public String index() {
        // This looks for a file named "index.html" in src/main/resources/templates
        return "home"; 
    }
}