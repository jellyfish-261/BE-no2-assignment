package com.example.schedule.service;

import com.example.schedule.dto.request.AuthorRequestDto;
import com.example.schedule.dto.response.AuthorResponseDto;
import com.example.schedule.entity.Author;
import com.example.schedule.repository.AuthorRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override // Lv3. 작성자 생성
    public AuthorResponseDto saveAuthor(AuthorRequestDto dto) {
        Author author = new Author(dto.getName(), dto.getEmail());
        return authorRepository.saveAuthor(author);
    }
}