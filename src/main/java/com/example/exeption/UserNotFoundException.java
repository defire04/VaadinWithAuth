package com.example.exeption;

public class UserNotFoundException extends LoginFormException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
