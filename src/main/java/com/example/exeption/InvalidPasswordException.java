package com.example.exeption;

public class InvalidPasswordException extends LoginFormException{
    public InvalidPasswordException(String message) {
        super(message);
    }
}
