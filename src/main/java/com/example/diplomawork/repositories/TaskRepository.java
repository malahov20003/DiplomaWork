package com.example.diplomawork.repositories;

import com.example.diplomawork.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Получить все задачи по ID назначенного сотрудника
    List<Task> findByAssignedToId(Long employeeId);

    // Получить все задачи, у которых уже загружен отчёт
    List<Task> findByAssignedToIdAndReportFileIsNotNull(Long employeeId);

    // Получить все задачи, у которых ещё нет отчёта
    List<Task> findByAssignedToIdAndReportFileIsNull(Long employeeId);

    // Можно добавить метод для всех задач, у которых просрочен дедлайн (если нужно)
    List<Task> findByAssignedToIdAndDeadlineBeforeAndReportFileIsNull(Long employeeId, java.time.LocalDate date);

    boolean existsByDescriptionAndAssignedToId(String description, Long userId);

}
