package com.example.diplomawork.controllers;

import com.example.diplomawork.entities.User;
import com.example.diplomawork.enums.Role;
import com.example.diplomawork.services.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Получить всех пользователей
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    // Получить краткую инфу (для index.html)
    @GetMapping("/short")
    public List<Map<String, Object>> getShortInfo() {
        return userService.getAll().stream().map(user -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", user.getId());
            map.put("name", user.getName());
            map.put("role", user.getRole().name());
            return map;
        }).toList();
    }


    // Получить по роли
    @GetMapping("/role/{role}")
    public List<User> getUsersByRole(@PathVariable Role role) {
        return userService.getByRole(role);
    }

    // Создать пользователя (JSON)
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }

    // Создать пользователя из формы (для users.html)
    @PostMapping("/form")
    public void createUserFromForm(@RequestParam String name, @RequestParam Role role) {
        User user = new User();
        user.setName(name);
        user.setRole(role);
        userService.save(user);
    }

    // Удалить пользователя
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    // Обновить роль
    @PutMapping("/{id}/role")
    public User changeUserRole(@PathVariable Long id, @RequestBody RoleChangeRequest request) {
        return userService.changeRole(id, request.getRole());
    }

    // Обновить профиль (имя, email и SMTP пароль)
    @PostMapping("/update-profile")
    public void updateProfile(@RequestParam Long userId,
                              @RequestParam String name,
                              @RequestParam String email,
                              @RequestParam String password) {
        userService.updateProfile(userId, name, email, password);
    }

    // Внутренний DTO для изменения роли
    public static class RoleChangeRequest {
        private Role role;

        public Role getRole() { return role; }
        public void setRole(Role role) { this.role = role; }
    }
}
