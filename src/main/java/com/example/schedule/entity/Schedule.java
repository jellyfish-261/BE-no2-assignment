package com.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {
    private Long id;
    private String task;
    private String authorName;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Schedule(String task, String author_name, String password) { // Lv1. 일정 생성
        this.task = task;
        this.authorName = author_name;
        this.password = password;
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    public Schedule(long id, String task, String authorName, LocalDateTime createdAt, LocalDateTime updatedAt) { // Lv1. 선택 일정 조회
        this.id = id;
        this.task = task;
        this.authorName = authorName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void updateTask(String task) { // Lv2. 선택 일정 수정
        this.task = task;
    }

    public void updateAuthorName(String authorName) { // Lv2. 선택 일정 수정
        this.authorName = authorName;
    }

    public void updateUpdatedAt(LocalDateTime now) { // Lv2. 선택 일정 수정
        this.updatedAt = now;
    }
}
