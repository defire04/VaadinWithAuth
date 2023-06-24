package com.example.data.service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PasswordService {

    private static final int PASSWORD_CONFIRMATION_LENGTH = 4;
    private static final int PASSWORD_LENGTH = 8;

    private static final String DIGITS = "0123456789";
    private static final String DIGITS_AND_LETTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private final BCryptPasswordEncoder passwordEncoder;

    public PasswordService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String generateRandomConfirmationPassword() {
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < PASSWORD_CONFIRMATION_LENGTH; i++) {
            int randomIndex = random.nextInt(DIGITS.length());
            char digit = DIGITS.charAt(randomIndex);
            password.append(digit);
        }
        return password.toString();
    }

    public String generateRandomPassword() {
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int randomIndex = random.nextInt(DIGITS_AND_LETTERS.length());
            char digit = DIGITS_AND_LETTERS.charAt(randomIndex);
            password.append(digit);
        }
        return password.toString();
    }

    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean matches(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
}