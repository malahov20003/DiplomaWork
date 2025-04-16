package com.example.diplomawork.repositories;

import com.example.diplomawork.entities.UserTask;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserTaskRepository extends JpaRepository<UserTask, Long> {
    List<UserTask> findByUserId(Long userId);
    List<UserTask> findByTaskId(Long taskId);
}


