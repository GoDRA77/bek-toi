package com.example.demo.controllers;

import com.example.demo.services.EmailVerificationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email-verification")
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;

    public EmailVerificationController(EmailVerificationService emailVerificationService) {
        this.emailVerificationService = emailVerificationService;
    }

    @PostMapping("/send-code")
    public String sendCode(@RequestParam String email) {
        return emailVerificationService.generateCode(email);
    }

    @PostMapping("/verify")
    public boolean verifyCode(@RequestParam String email, @RequestParam String code) {
        return emailVerificationService.verifyCode(email, code);
    }
}
