package com.example.diplomawork.controllers;

import com.example.diplomawork.entities.UserTask;
import com.example.diplomawork.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ReportPageController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/manager/reports")
    public String showManagerReports(@RequestParam(name = "userId", required = false) Long managerId, Model model) {
        if (managerId == null) {
            return "redirect:/";
        }

        List<UserTask> userTasks = taskService.getAllUserTasks();
        model.addAttribute("userTasks", userTasks);
        return "manager/reports";
    }

}
