package com.example.schedule.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter  // Lv4. 페이지네이션
@AllArgsConstructor
public class Paging {
    private int page;
    private int size;
}
