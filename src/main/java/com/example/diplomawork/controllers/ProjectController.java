package com.example.diplomawork.controllers;

import com.example.diplomawork.entities.Project;
import com.example.diplomawork.entities.User;
import com.example.diplomawork.services.ProjectService;
import com.example.diplomawork.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Контроллер для управления проектами
 */
@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    /**
     * Получить список всех проектов
     */
    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAll();
    }

    /**
     * Создать новый проект
     * Ожидается JSON: { "title": "...", "managerId": 1 }
     */
    @PostMapping
    public Project createProject(@RequestBody Map<String, String> body) {
        String title = body.get("title");
        Long managerId = Long.parseLong(body.get("managerId"));
        return projectService.create(title, managerId);
    }

    /**
     * Удалить проект по ID
     */
    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectService.delete(id);
    }
}
