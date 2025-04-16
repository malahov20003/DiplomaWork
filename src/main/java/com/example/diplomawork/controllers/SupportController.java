package com.example.diplomawork.controllers;

import com.example.diplomawork.entities.SupportRequest;
import com.example.diplomawork.entities.User;
import com.example.diplomawork.services.SupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/support")
@CrossOrigin(origins = "*")
public class SupportController {

    @Autowired
    private SupportService supportService;

    // Получить все обращения
    @GetMapping
    public List<SupportRequest> getAllRequests() {
        return supportService.getAll();
    }

    // Создать обращение
    @PostMapping
    public SupportRequest createSupportRequest(@RequestBody Map<String, String> body) {
        String message = body.get("message");
        Long senderId = Long.parseLong(body.get("senderId"));
        return supportService.create(message, senderId);
    }

    // Загрузить отчёт в поддержку
    @PostMapping("/upload-report")
    public ResponseEntity<String> uploadReport(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId
    ) throws IOException {
        String fileName = file.getOriginalFilename();
        Path path = Paths.get("uploads/" + fileName);
        Files.write(path, file.getBytes());

        SupportRequest request = new SupportRequest();
        request.setMessage("Файл отчёта: " + fileName);
        request.setSender(new User(userId)); // убедись, что есть конструктор User(Long id)

        supportService.save(request);
        return ResponseEntity.ok("Отчёт загружен");
    }

    // Получить обращения пользователя (переименованный метод, чтобы не дублировать)
    @GetMapping("/by-user/{id}")
    public List<SupportRequest> getRequestsByUser(@PathVariable Long id) {
        return supportService.getAll().stream()
                .filter(r -> r.getSender() != null && r.getSender().getId().equals(id))
                .collect(Collectors.toList());
    }
}

