package com.example.diplomawork.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()  // Разрешить доступ ко всем URL
                )
                .csrf(AbstractHttpConfigurer::disable) // Отключить защиту от CSRF (можно включить потом)
                .formLogin(AbstractHttpConfigurer::disable) // Отключить стандартную форму входа
                .httpBasic(AbstractHttpConfigurer::disable); // Отключить базовую авторизацию
        return http.build();
    }
}
