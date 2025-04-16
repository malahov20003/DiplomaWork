package com.example.diplomawork.repositories;

import com.example.diplomawork.entities.User;
import com.example.diplomawork.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByRole(Role role);
}


