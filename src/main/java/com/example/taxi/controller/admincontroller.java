package com.example.taxi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.taxi.model.rentalmodel;
import com.example.taxi.service.driverservice;
import com.example.taxi.service.rentalservice;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class admincontroller {

    @Autowired
    driverservice driverService;

    @Autowired
    rentalservice rentalService;

    // --- SIDEBAR NAVIGATION MAPPINGS ---
    
    // UPDATED: Now fetches fleet data to prevent null pointer exceptions
    @GetMapping("/adminhome")
    public String adminhome(Model m, HttpSession session) {
        if (session.getAttribute("admin_logged_in") == null)
            return "redirect:/admin-login";
        
        // Fetch all cars for the inventory table and stats
        List<rentalmodel> allCars = rentalService.getAllCars();
        // Fallback to empty list if null to prevent crashes
        m.addAttribute("allCars", (allCars != null) ? allCars : new ArrayList<>());
        
        return "admin"; // This maps to admin.html
    }

    @GetMapping("/driver-approvals")
    public String driverApprovals(Model m, HttpSession session) {
        if (session.getAttribute("admin_logged_in") == null)
            return "redirect:/admin-login";
        m.addAttribute("drivers", driverService.getPending());
        return "driver_approvals";
    }

    @GetMapping("/rental-approvals")
    public String rentalApprovals(Model m, HttpSession session) {
        if (session.getAttribute("admin_logged_in") == null)
            return "redirect:/admin-login";
        
        m.addAttribute("rentals", rentalService.getPendingRentals());
        return "rental_approvals";
    }

    // --- ADD RENTAL CAR FUNCTIONALITY ---

    @GetMapping("/add-rental")
    public String showAddCarForm(Model m, HttpSession session) {
        if (session.getAttribute("admin_logged_in") == null)
            return "redirect:/admin-login";
        m.addAttribute("rentalmodel", new rentalmodel());
        return "add_rental";
    }

    @PostMapping("/add-rental")
    public String saveRentalCar(@ModelAttribute rentalmodel car, HttpSession session) {
        if (session.getAttribute("admin_logged_in") == null)
            return "redirect:/admin-login";

        car.setStatus("Available");
        car.setAccepted(false);
        car.setApproved(false);
        rentalService.saveRental(car);
        return "redirect:/admin/rental-approvals";
    }

    // --- APPROVAL & MANAGEMENT ACTIONS ---

    @PostMapping("/approve-driver/{id}")
    public String approveDriver(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("admin_logged_in") == null)
            return "redirect:/admin-login";
        driverService.approve(id);
        return "redirect:/admin/driver-approvals";
    }

    @PostMapping("/approve-rental/{id}")
    public String approveRental(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("admin_logged_in") == null)
            return "redirect:/admin-login";
        
        rentalmodel rental = rentalService.findById(id);
        
        if (rental != null && rental.getUser() != null) {
            rentalService.approveRental(id, rental.getUser().getId());
        }
        return "redirect:/admin/rental-approvals";
    }

    // UPDATED: Added safety check to ensure only 'Available' cars can be deleted
    @PostMapping("/delete-rental/{id}")
    public String deleteRentalCar(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("admin_logged_in") == null)
            return "redirect:/admin-login";
            
        rentalmodel car = rentalService.findById(id);
        if (car != null && "Available".equals(car.getStatus())) {
            rentalService.deleteRental(id);
        }
        return "redirect:/admin/adminhome";
    }
}