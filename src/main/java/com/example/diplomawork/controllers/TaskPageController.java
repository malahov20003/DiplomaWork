package com.example.diplomawork.controllers;

import com.example.diplomawork.entities.Task;
import com.example.diplomawork.entities.User;
import com.example.diplomawork.entities.UserTask;
import com.example.diplomawork.services.TaskService;
import com.example.diplomawork.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TaskPageController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @GetMapping("/manager/tasks.html")
    public String showTasksPage(@RequestParam(name = "userId", required = false) Long userId, Model model) {
        if (userId == null) {
            return "redirect:/";
        }

        List<User> employees = userService.getAllEmployees(); // фильтрация по роли EMPLOYEE
        List<Task> tasks = taskService.getAll();
        List<UserTask> userTasks = taskService.getAllUserTasks();

        model.addAttribute("users", employees);
        model.addAttribute("tasks", tasks);
        model.addAttribute("userTasks", userTasks);
        model.addAttribute("userId", userId);

        return "manager/tasks";
    }


}

