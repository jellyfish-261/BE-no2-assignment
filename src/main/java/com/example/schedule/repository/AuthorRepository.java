package com.example.schedule.repository;

import com.example.schedule.dto.response.AuthorResponseDto;
import com.example.schedule.entity.Author;

public interface AuthorRepository {
    AuthorResponseDto saveAuthor(Author author); // Lv3. 작성자 생성
}
