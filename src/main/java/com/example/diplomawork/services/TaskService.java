package com.example.diplomawork.services;

import com.example.diplomawork.entities.Task;
import com.example.diplomawork.entities.User;
import com.example.diplomawork.entities.UserTask;
import com.example.diplomawork.enums.TaskStatus;
import com.example.diplomawork.repositories.ProjectRepository;
import com.example.diplomawork.repositories.TaskRepository;
import com.example.diplomawork.repositories.UserRepository;
import com.example.diplomawork.repositories.UserTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTaskRepository userTaskRepository;

    // ✅ Получение всех задач (общих)
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    // ✅ Получение задач по userId через связующую таблицу
    public List<UserTask> getUserTasksByUserId(Long userId) {
        return userTaskRepository.findByUserId(userId);
    }



    // ✅ Назначение задания нескольким сотрудникам
    public Task assignTaskWithUsers(String description, LocalDate startDate, LocalDate deadline, List<Long> userIds) {
        Task task = new Task();
        task.setDescription(description);
        task.setFilePath(description); // вот эта строка — важна
        task.setStartDate(startDate);
        task.setDeadline(deadline);
        task.setStatus(TaskStatus.NOT_STARTED);
        task = taskRepository.save(task);

        for (Long userId : userIds) {
            Optional<User> userOpt = userRepository.findById(userId);
            if (userOpt.isPresent()) {
                UserTask userTask = new UserTask();
                userTask.setTask(task);
                userTask.setUser(userOpt.get());
                userTask.setStatus(TaskStatus.ASSIGNED);
                userTaskRepository.save(userTask);
            }
        }

        return task;
    }

    // ✅ Загрузка отчёта сотрудником по задаче
    public void attachReportToUserTask(Long taskId, Long userId, String fileName) {
        List<UserTask> userTasks = userTaskRepository.findByTaskId(taskId);
        for (UserTask userTask : userTasks) {
            if (userTask.getUser().getId().equals(userId)) {
                userTask.setReportFile(fileName);
                userTask.setUploadDate(LocalDate.now());
                userTask.setStatus(TaskStatus.COMPLETED);
                userTaskRepository.save(userTask);
                break;
            }
        }
    }

    public void updateTaskFile(Long taskId, String newFileName) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Задание не найдено по ID: " + taskId));
        task.setFilePath(newFileName);
        task.setDescription(newFileName);
        taskRepository.save(task);
    }

    public List<UserTask> getAllUserTasks() {
        return userTaskRepository.findAll();
    }


    // ✅ Удаление задачи
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
