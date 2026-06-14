package com.example.taxi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpSession;

import com.example.taxi.model.roundmodel;
import com.example.taxi.service.roundservice;
import com.example.taxi.service.userservice;

@Controller
public class roundcontroller {
    @Autowired
    roundservice roundserv;

    @Autowired
    userservice userServ;

    @GetMapping("/roundtrip")
    public String loadData(Model m, HttpSession session) {
        // Security check
        if (session.getAttribute("user_id") == null) {
            return "redirect:/login";
        }
        m.addAttribute("roundmodel", new roundmodel());
        return "round";
    }

    @PostMapping("/roundtrip")
    public String getDetails(@ModelAttribute roundmodel round, HttpSession session) {
        Long userId = (Long) session.getAttribute("user_id");
        if (userId == null)
            return "redirect:/login";

        // Link User and set status
        round.setUser(userServ.findById(userId));
        round.setStatus("Pending");
        
        // CRITICAL: Set bookingType so 'myrides.html' can filter this row
        round.setBookingType("Round-Trip");
        
        roundserv.savedate(round);
        return "redirect:/booking-success";
    }
}