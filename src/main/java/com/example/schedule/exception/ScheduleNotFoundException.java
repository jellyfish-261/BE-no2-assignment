package com.example.schedule.exception;

// Lv5. 예외 발생 처리
public class ScheduleNotFoundException extends RuntimeException{
    public ScheduleNotFoundException(String message) {
        super(message);
    }
}
