package com.example.diplomawork.controllers;

import com.example.diplomawork.entities.User;
import com.example.diplomawork.repositories.UserRepository;
import com.example.diplomawork.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class UserProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    // Открытие профиля
    @GetMapping("/employeeprofile")
    public String showEProfile(@RequestParam Long userId, Model model) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
            return "/employee/EmployeeProfile";
        }
        return "redirect:/";
    }

    @GetMapping("/managerprofile")
    public String showMProfile(@RequestParam Long userId, Model model) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
            return "/manager/ManagerProfile";
        }
        return "redirect:/";
    }

    // Сохранение профиля
    @PostMapping("/profile/save")
    public String saveProfile(@ModelAttribute User user) {
        userService.updateProfile(user);
        return "redirect:/profile?userId=" + user.getId();
    }
}
