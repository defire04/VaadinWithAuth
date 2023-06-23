package com.example.exeption;

public class LoginFormException extends RuntimeException{
    public LoginFormException(String message) {
        super(message);
    }
}
