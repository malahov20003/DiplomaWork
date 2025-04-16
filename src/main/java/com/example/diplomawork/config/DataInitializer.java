package com.example.diplomawork.config;

import com.example.diplomawork.entities.User;
import com.example.diplomawork.enums.Role;
import com.example.diplomawork.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;

    public DataInitializer(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        if (userService.getAll().isEmpty()) {
            userService.save(new User( "Администратор", Role.ADMIN));
            userService.save(new User( "Руководитель", Role.MANAGER));
            userService.save(new User( "Сотрудник 1", Role.EMPLOYEE));
            userService.save(new User( "Сотрудник 2", Role.EMPLOYEE));
            userService.save(new User( "Сотрудник 3", Role.EMPLOYEE));
        }
    }
}
