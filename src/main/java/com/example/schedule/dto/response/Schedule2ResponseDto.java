package com.example.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule2ResponseDto {
    private Long id;
    private String task;
    private Long authorId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
