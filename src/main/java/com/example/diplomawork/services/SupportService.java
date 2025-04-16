package com.example.diplomawork.services;

import com.example.diplomawork.entities.SupportRequest;
import com.example.diplomawork.entities.User;
import com.example.diplomawork.repositories.SupportRepository;
import com.example.diplomawork.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;


@Service
public class SupportService {

    @Autowired
    private SupportRepository supportRepository;

    @Autowired
    private UserRepository userRepository;

    public List<SupportRequest> getAll() {
        return supportRepository.findAll();
    }

    public SupportRequest create(String message, Long senderId) {
        Optional<User> senderOpt = userRepository.findById(senderId);

        if (senderOpt.isEmpty()) {
            throw new RuntimeException("Пользователь (отправитель) не найден.");
        }

        SupportRequest request = new SupportRequest();
        request.setMessage(message);
        request.setSender(senderOpt.get());

        return supportRepository.save(request);
    }

    public void save(SupportRequest request) {
    }

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmailToEmployee(String toEmail, String subject, String messageBody, String fromEmail, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(messageBody);
        mailSender.send(message);
    }

}

