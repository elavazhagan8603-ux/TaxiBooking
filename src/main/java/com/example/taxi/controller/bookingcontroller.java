package com.example.taxi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpSession;

import com.example.taxi.model.bookingmodel;
import com.example.taxi.model.taximodel;
import com.example.taxi.service.bookingservice;
import com.example.taxi.service.userservice;

@Controller
public class bookingcontroller {

    @Autowired
    bookingservice bookingserv;

    @Autowired
    userservice userServ;

    @GetMapping("/onewaybook")
    public String loadData(Model m, HttpSession session) {
        // Security Check: Redirect if not logged in
        if (session.getAttribute("user_id") == null) {
            return "redirect:/login";
        }
        m.addAttribute("bookingmodel", new bookingmodel());
        return "oneway";
    }

    @PostMapping("/onewaybook")
    public String getDetails(@ModelAttribute bookingmodel booking, HttpSession session) {
        // 1. Get logged-in user ID
        Long userId = (Long) session.getAttribute("user_id");
        if (userId == null) {
            return "redirect:/login";
        }

        // 2. Link the booking to the logged-in user
        taximodel currentUser = userServ.findById(userId);
        booking.setUser(currentUser);
        
        // 3. Set standard defaults
        booking.setAccepted(false);      // Pending until Admin/Driver accepts
        booking.setBookingType("One-Way"); // Crucial for filtering in 'My Rides'
        booking.setStatus("Pending");    // Consistent with your other services
        
        // 4. Save to database
        bookingserv.savedate(booking);
        
        return "redirect:/booking-success";
    }

    @GetMapping("/booking-success")
    public String getSuccessPage(Model m) {
        m.addAttribute("rideType", "One-Way");
        return "success"; 
    }
}