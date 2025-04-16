package com.example.diplomawork.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToOne;
import com.example.diplomawork.entities.User;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(nullable = false)
//    private String title;

    @ManyToOne
    private User manager;

    // Конструкторы
    public Project() {}

    public Project(Long id, String title, User manager) {
        this.id = id;
        //       this.title = title;
        this.manager = manager;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public String getTitle() {
//        return title;
    //   }

    //   public void setTitle(String title) {
//        this.title = title;
    //   }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public void setTitle(String title) {
    }
}
