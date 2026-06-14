package com.example.taxi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.taxi.model.drivermodel;
import com.example.taxi.service.driverservice;
import jakarta.servlet.http.HttpSession;

@Controller
public class drivercontroller {

    @Autowired
    driverservice driverServ;

    // --- Signup ---
    @GetMapping("/driversign")
    public String showSignup(Model m) {
        m.addAttribute("driver", new drivermodel());
        return "drivers";
    }

    @PostMapping("/driversign")
    public String register(@ModelAttribute drivermodel driver) {
        // Ensure new drivers default to pending approval
        driver.setApproved(false); 
        driverServ.register(driver);
        return "redirect:/driverlogin";
    }

    // --- Login ---
    @GetMapping("/driverlogin")
    public String showLogin() {
        return "driverlog";
    }

    @PostMapping("/driverlogin")
    public String logindata(@RequestParam("username") String username, 
                            @RequestParam("password") String password, 
                            HttpSession session, 
                            Model m) {
        
        drivermodel driver = driverServ.login(username, password);
        
        // 1. Verify existence
        // 2. CRITICAL: Verify Admin has approved the driver
        if (driver != null && driver.isApproved()) {
            session.setAttribute("driver_id", driver.getId());
            return "redirect:/driverhome"; 
        } else if (driver != null && !driver.isApproved()) {
            m.addAttribute("error", "Your account is currently pending Admin approval.");
            return "driverlog";
        } else {
            m.addAttribute("error", "Invalid username or password.");
            return "driverlog";
        }
    }
}