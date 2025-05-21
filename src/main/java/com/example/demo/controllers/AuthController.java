package com.example.demo.controllers;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "API для регистрации, подтверждения и входа")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final Random random = new Random();

    @PostMapping("/register")
    @Operation(summary = "Регистрация нового пользователя")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email уже зарегистрирован");
        }

        if (request.getName() == null || request.getName().isBlank()) {
            return ResponseEntity.badRequest().body("Имя обязательно");
        }

        String code = String.valueOf(1000 + random.nextInt(9000)); // 4-значный код

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setConfirmationCode(code);
        user.setConfirmed(false);
        userRepository.save(user);

        // отправка письма
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Код подтверждения");
        message.setText("Ваш код подтверждения: " + code);
        mailSender.send(message);

        return ResponseEntity.ok("Код отправлен на почту");
    }

    @PostMapping("/login")
    @Operation(summary = "Вход пользователя")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (!user.isConfirmed()) {
                return ResponseEntity.status(403).body("Email не подтвержден");
            }

            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return ResponseEntity.ok("Успешный вход");
            }
        }
        return ResponseEntity.status(401).body("Неверный email или пароль");
    }

    @PostMapping("/verify")
    @Operation(summary = "Подтверждение email по коду")
    public ResponseEntity<String> verify(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String code = body.get("code");

        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getConfirmationCode().equals(code)) {
                user.setConfirmed(true);
                user.setConfirmationCode(null);
                userRepository.save(user);
                return ResponseEntity.ok("Email подтвержден");
            } else {
                return ResponseEntity.badRequest().body("Неверный код");
            }
        }
        return ResponseEntity.badRequest().body("Пользователь не найден");
    }

    @GetMapping("/profile")
    @Operation(summary = "Получение профиля авторизованного пользователя")
    public ResponseEntity<User> getProfile(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        return ResponseEntity.ok(user);
    }
}
