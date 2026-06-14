package com.example.taxi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
public class adminlogcontroller {

    // Default hardcoded admin credentials
    private final String ADMIN_USER = "ela";
    private final String ADMIN_PASS = "123";

    @GetMapping("/admin-login")
    public String showLogin() {
        return "adminlog"; 
    }

    @PostMapping("/admin-login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model m) {

        if (ADMIN_USER.equals(username) && ADMIN_PASS.equals(password)) {
            // Set the flag to indicate admin access
            session.setAttribute("admin_logged_in", true);
            
            // Redirecting to the new entry point of your admin panel
            return "redirect:/admin/adminhome"; 
        }

        m.addAttribute("error", "Invalid admin credentials!");
        return "adminlog";
    }

    // Optional: Add a logout mapping for safety
    @GetMapping("/admin-logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin-login";
    }
}