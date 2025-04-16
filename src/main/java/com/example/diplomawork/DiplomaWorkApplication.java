package com.example.diplomawork;

import com.example.diplomawork.entities.User;
import com.example.diplomawork.enums.Role;
import com.example.diplomawork.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DiplomaWorkApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiplomaWorkApplication.class, args);
    }

    // Инициализация пользователей при первом запуске
    @Bean
    public CommandLineRunner initUsers(UserRepository userRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                userRepository.save(new User("Администратор", Role.ADMIN));
                userRepository.save(new User("Руководитель", Role.MANAGER));
                userRepository.save(new User("Сотрудник 1", Role.EMPLOYEE));
                userRepository.save(new User("Сотрудник 2", Role.EMPLOYEE));
                userRepository.save(new User("Сотрудник 3", Role.EMPLOYEE));
            }
        };
    }
}

