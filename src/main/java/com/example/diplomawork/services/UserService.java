package com.example.diplomawork.services;

import com.example.diplomawork.entities.User;
import com.example.diplomawork.enums.Role;
import com.example.diplomawork.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> getByRole(Role role) {
        return userRepository.findAll().stream()
                .filter(user -> user.getRole() == role)
                .toList();
    }

    public User changeRole(Long id, Role newRole) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setRole(newRole);
            return userRepository.save(user);
        }
        throw new RuntimeException("User not found");
    }

    public void updateProfile(Long userId, String name, String email, String password) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setName(name);
        user.setSmtpEmail(email);
        user.setSmtpPassword(password);
        userRepository.save(user);
    }

    public List<User> getAllEmployees() {
        return userRepository.findByRole(Role.EMPLOYEE);
    }

    @PostConstruct
    public void initDefaultUsers() {
        if (userRepository.count() == 0) {
            userRepository.save(new User("Администратор", Role.ADMIN));
            userRepository.save(new User("Руководитель", Role.MANAGER));
            userRepository.save(new User("Сотрудник 1", Role.EMPLOYEE));
            userRepository.save(new User("Сотрудник 2", Role.EMPLOYEE));
            userRepository.save(new User("Сотрудник 3", Role.EMPLOYEE));
        }
    }

    public void updateProfile(User userData) {
        Optional<User> userOpt = userRepository.findById(userData.getId());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setName(userData.getName());
            user.setSmtpEmail(userData.getSmtpEmail());
            user.setSmtpPassword(userData.getSmtpPassword());
            userRepository.save(user);
        }
    }

    public boolean sendEmail(Long senderId, Long recipientId, String messageText) {
        Optional<User> senderOpt = userRepository.findById(senderId);
        Optional<User> recipientOpt = userRepository.findById(recipientId);

        if (senderOpt.isPresent() && recipientOpt.isPresent()) {
            User sender = senderOpt.get();
            User recipient = recipientOpt.get();

            if (sender.getSmtpEmail() == null || sender.getSmtpPassword() == null) {
                throw new IllegalStateException("Введите данные в личном кабинете");
            }

            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost("smtp.gmail.com"); // Можно изменить на другой SMTP
            mailSender.setPort(587);
            mailSender.setUsername(sender.getSmtpEmail());
            mailSender.setPassword(sender.getSmtpPassword());

            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "true");

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(sender.getSmtpEmail());
            message.setTo(recipient.getSmtpEmail());
            message.setSubject("Сообщение по заданию");
            message.setText(messageText);

            mailSender.send(message);
            return true;
        }
        return false;
    }
}
