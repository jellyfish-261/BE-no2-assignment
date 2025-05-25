package com.example.schedule.exception;

// Lv5. 예외 발생 처리
public class PasswordMismatchException extends RuntimeException{
    public PasswordMismatchException(String message) {
        super(message);
    }
}