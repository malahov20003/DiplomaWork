package com.example.diplomawork.services;

import com.example.diplomawork.entities.Project;
import com.example.diplomawork.entities.User;
import com.example.diplomawork.repositories.ProjectRepository;
import com.example.diplomawork.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Получить все проекты
     */
    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    /**
     * Создать новый проект
     */
    public Project create(String title, Long managerId) {
        Optional<User> managerOpt = userRepository.findById(managerId);
        if (managerOpt.isEmpty()) {
            throw new RuntimeException("Руководитель не найден с id: " + managerId);
        }

        User manager = managerOpt.get();
        Project project = new Project();
        project.setTitle(title);
        project.setManager(manager);

        return projectRepository.save(project);
    }

    /**
     * Удалить проект по ID
     */
    public void delete(Long id) {
        projectRepository.deleteById(id);
    }
}
