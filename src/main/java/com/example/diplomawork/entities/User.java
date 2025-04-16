package com.example.diplomawork.entities;

import com.example.diplomawork.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String smtpEmail;
    private String smtpPassword;

    @Getter
    @Setter
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<UserTask> userTasks = new ArrayList<>();


    // Конструкторы
    public User() {}

    public User(String name, Role role) {
        this.name = name;
        this.role = role;
    }

    public User(Long id) {
        this.id = id;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public String getSmtpEmail() {
        return smtpEmail;
    }

    public String getSmtpPassword() {
        return smtpPassword;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setSmtpEmail(String smtpEmail) {
        this.smtpEmail = smtpEmail;
    }

    public void setSmtpPassword(String smtpPassword) {
        this.smtpPassword = smtpPassword;
    }
}
