package com.example.schedule.entity;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Schedule2 {
    private Long id;
    private String task;
    private Long authorId;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Schedule2(String task, Long authorId, String password) { // Lv3. 일정 생성
        this.task = task;
        this.authorId = authorId;
        this.password = password;
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }
}
