package com.example.schedule.entity;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Author {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Author(String name, String email) { // Lv3. 작성자 생성
        this.name = name;
        this.email = email;
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }
}
