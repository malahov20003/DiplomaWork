package com.example.diplomawork.controllers;

import com.example.diplomawork.entities.Task;
import com.example.diplomawork.enums.TaskStatus;
import com.example.diplomawork.services.TaskService;
import com.example.diplomawork.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAll();
    }

    // ✅ Получение задач сотрудника через UserTask
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<?> getTasksByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(taskService.getUserTasksByUserId(employeeId));
    }

    // ✅ Загрузка задания и назначение нескольким сотрудникам
    @PostMapping(value = "/upload-task", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadTask(
            @RequestParam("file") MultipartFile file,
            @RequestParam("assignedTo") List<Long> userIds,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) throws IOException {

        String fileName = file.getOriginalFilename();
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);
        Path filePath = uploadPath.resolve(fileName);
        Files.write(filePath, file.getBytes());

        taskService.assignTaskWithUsers(fileName, LocalDate.parse(startDate), LocalDate.parse(endDate), userIds);
        return ResponseEntity.ok("Задание назначено!");
    }

    // ✅ Повторная загрузка файла задания
    @PostMapping(value = "/reupload-task", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> reuploadTaskFile(
            @RequestParam("taskId") Long taskId,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        String fileName = file.getOriginalFilename();
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);
        Path filePath = uploadPath.resolve(fileName);
        Files.write(filePath, file.getBytes());

        taskService.updateTaskFile(taskId, fileName);
        return ResponseEntity.ok("Файл задания обновлён");
    }

    // ✅ Скачивание файла задания
    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadTaskFile(@PathVariable String filename) throws MalformedURLException {
        Path filePath = Paths.get(UPLOAD_DIR).resolve(filename);
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    // ✅ Загрузка отчёта от сотрудника
    @PostMapping(value = "/upload-report", consumes = {"multipart/form-data"})
    public ResponseEntity<String> uploadReport(
            @RequestParam("taskId") Long taskId,
            @RequestParam("userId") Long userId,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        Path uploadPath = Paths.get(UPLOAD_DIR + "reports/");
        if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);

        String fileName = "report_task_" + taskId + "_user_" + userId + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.write(filePath, file.getBytes());

        taskService.attachReportToUserTask(taskId, userId, fileName);
        return ResponseEntity.ok("Отчёт загружен.");
    }

    @PostMapping(value = "/reupload-report", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> reuploadReport(
            @RequestParam("taskId") Long taskId,
            @RequestParam("userId") Long userId,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        Path uploadPath = Paths.get(UPLOAD_DIR + "reports/");
        if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);

        String fileName = "report_task_" + taskId + "_user_" + userId + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.write(filePath, file.getBytes());

        taskService.attachReportToUserTask(taskId, userId, fileName); // та же логика
        return ResponseEntity.ok("Отчёт заменён.");
    }


    // ✅ Скачивание отчёта для руководителя
    @GetMapping("/download-report/{filename:.+}")
    public ResponseEntity<Resource> downloadReportFile(@PathVariable String filename) throws MalformedURLException {
        Path filePath = Paths.get(UPLOAD_DIR + "reports/").resolve(filename);
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    // ✅ Отправка email-сообщения по SMTP пользователя
    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmail(
            @RequestParam("senderId") Long senderId,
            @RequestParam("recipientId") Long recipientId,
            @RequestParam("message") String messageText
    ) {
        try {
            boolean result = userService.sendEmail(senderId, recipientId, messageText);
            if (result) {
                return ResponseEntity.ok("Сообщение отправлено");
            } else {
                return ResponseEntity.badRequest().body("Не удалось отправить сообщение");
            }
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body("Введите данные в личном кабинете");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Ошибка при отправке письма: " + e.getMessage());
        }
    }
}