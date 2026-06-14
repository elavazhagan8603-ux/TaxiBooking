package com.example.taxi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.taxi.model.taximodel;
import com.example.taxi.service.taxiservice;
import jakarta.servlet.http.HttpSession;

@Controller
public class taxicontroller {
    @Autowired
    taxiservice taxiServ;

    @GetMapping("/first")
    public String home() {
        return "home";
    }

    @GetMapping("/signup")
    public String loadDetails(Model m) {
        m.addAttribute("user", new taximodel());
        return "booking"; 
    }

    @PostMapping("/signup")
    public String getDetails(@ModelAttribute taximodel user) {
        taxiServ.savedate(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String booklogin() {
        return "custlogin";
    }

    @PostMapping("/login")
    public String logindata(@RequestParam("username") String username,
            @RequestParam("password") String password,
            Model m,
            HttpSession session) {
        taximodel user = taxiServ.checkData(username, password);

        if (user != null) {
            // Standardizing session attribute
            session.setAttribute("user_id", user.getId());
            // Using redirect to ensure the user is officially logged in
            // and the session is active before they reach the home page
            return "redirect:/bookinghome";
        } else {
            m.addAttribute("error", "Invalid username or password");
            return "custlogin";
        }
    }

    @GetMapping("/bookinghome")
    public String bookingHome(HttpSession session) {
        // Security check: Ensure only logged-in users reach the dashboard
        if (session.getAttribute("user_id") == null) {
            return "redirect:/login";
        }
        return "bookinghome";
    }

    // --- Static Pages ---
    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }
}