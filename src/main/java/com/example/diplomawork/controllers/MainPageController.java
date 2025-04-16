package com.example.diplomawork.controllers;

import com.example.diplomawork.enums.Role;
import com.example.diplomawork.services.TaskService;
import com.example.diplomawork.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainPageController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }

    @GetMapping("/admin/users.html")
    public String adminUsersPage(Model model) {
        model.addAttribute("users", userService.getAll());
        return "admin/users";
    }

    // ✅ ОБНОВЛЁННЫЙ метод — теперь с userTasks + userId
    @GetMapping("/manager/reports.html")
    public String managerReports(@RequestParam(name = "userId", required = false) Long userId, Model model) {
        if (userId == null) return "redirect:/";

        model.addAttribute("userId", userId);
        model.addAttribute("userTasks", taskService.getAllUserTasks());
        return "manager/reports";
    }

    @GetMapping("/employee/dashboard.html")
    public String employeeDashboardWithParam(
            @RequestParam(name = "userId", required = false) Long userId, Model model) {

        if (userId == null) {
            return "redirect:/";
        }

        model.addAttribute("userId", userId);
        model.addAttribute("userTasks", taskService.getUserTasksByUserId(userId));
        return "employee/dashboard";
    }

    @GetMapping("/manager/{id}/tasks.html")
    public String managerPage(@PathVariable Long id, Model model) {
        model.addAttribute("userId", id);
        return "manager/tasks";
    }
}
