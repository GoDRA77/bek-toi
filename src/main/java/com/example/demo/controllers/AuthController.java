package com.example.demo.controllers;

import org.springframework.security.core.Authentication;
import com.example.demo.dto.AuthRequest;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "API для регистрации и входа")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    @Operation(summary = "Регистрация нового пользователя")
    public ResponseEntity<String> register(@RequestBody AuthRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email уже зарегистрирован");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok("Пользователь зарегистрирован");
    }

    @PostMapping("/login")
    @Operation(summary = "Вход пользователя")
    public ResponseEntity<String> login(@RequestBody AuthRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isPresent() && passwordEncoder.matches(request.getPassword(), userOpt.get().getPassword())) {
            return ResponseEntity.ok("Успешный вход");
        }
        return ResponseEntity.status(401).body("Неверный email или пароль");
    }
    public ResponseEntity<User> getProfile(Authentication authentication) {
        String email = authentication.getName(); // Получаем email текущего пользователя
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(user);
    }
}
