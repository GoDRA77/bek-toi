package com.example.demo.services;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailVerificationService {

    private final Map<String, String> verificationCodes = new HashMap<>();
    private final SecureRandom random = new SecureRandom();

    public String generateCode(String email) {
        String code = String.format("%06d", random.nextInt(999999));
        verificationCodes.put(email, code);
        return code;
    }

    public boolean verifyCode(String email, String code) {
        return code.equals(verificationCodes.get(email));
    }
}
