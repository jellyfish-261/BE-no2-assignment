package com.example.schedule.service;

import com.example.schedule.dto.request.AuthorRequestDto;
import com.example.schedule.dto.response.AuthorResponseDto;

public interface AuthorService {
    public AuthorResponseDto saveAuthor(AuthorRequestDto dto); // Lv3. 작성자 생성
}
