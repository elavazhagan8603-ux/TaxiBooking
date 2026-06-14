package com.example.taxi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.taxi.model.rentalmodel;
import com.example.taxi.model.taximodel;
import com.example.taxi.service.rentalservice;
import com.example.taxi.service.userservice;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/rental")
public class rentalcontroller {

    @Autowired
    rentalservice rentalService;

    @Autowired
    userservice userService;

    @GetMapping("/home")
    public String showAvailableCars(Model m, HttpSession session) {
        if (session.getAttribute("user_id") == null)
            return "redirect:/login";
        m.addAttribute("availableCars", rentalService.getAvailableCars());
        return "rentalhome";
    }

    // FIXED METHOD: Fetch the car and add to model
    @GetMapping("/form/{id}")
    public String showForm(@PathVariable Long id, Model m, HttpSession session) {
        if (session.getAttribute("user_id") == null)
            return "redirect:/login";

        rentalmodel car = rentalService.findById(id);
        m.addAttribute("car", car); // <-- THIS FIXES THE "car.carName" NULL ERROR
        m.addAttribute("carId", id);
        m.addAttribute("rental", new rentalmodel());
        return "rentalform";
    }

    @PostMapping("/submit/{id}")
    public String submitRental(@PathVariable Long id, @ModelAttribute rentalmodel rentalForm, HttpSession session) {
        Long userId = (Long) session.getAttribute("user_id");
        if (userId == null)
            return "redirect:/login";

        rentalmodel existingCar = rentalService.findById(id);
        existingCar.setDays(rentalForm.getDays());
        existingCar.setTotalPrice(rentalForm.getTotalPrice());
        existingCar.setUser(userService.findById(userId));
        existingCar.setStatus("Pending");

        rentalService.saveRental(existingCar);
        return "redirect:/rental/success";
    }

    @PostMapping("/complete/{id}")
    public String completeRental(@PathVariable Long id, HttpSession session) {
        Long userId = (Long) session.getAttribute("user_id");
        if (userId == null)
            return "redirect:/login";

        rentalmodel car = rentalService.findById(id);
        if (car.getUser() != null && car.getUser().getId().equals(userId)) {
            rentalService.completeTrip(id);
        }
        return "redirect:/my-rides";
    }

    @PostMapping("/admin/delete/{id}")
    public String deleteRental(@PathVariable Long id) {
        rentalmodel car = rentalService.findById(id);
        if (car != null && "Available".equals(car.getStatus())) {
            rentalService.deleteById(id);
        }
        return "redirect:/admin/adminhome";
    }

    @GetMapping("/success")
    public String showSuccess() {
        return "bookinghome";
    }
}