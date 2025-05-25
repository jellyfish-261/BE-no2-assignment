package com.example.schedule.controller;

import com.example.schedule.dto.request.AuthorRequestDto;
import com.example.schedule.dto.response.AuthorResponseDto;
import com.example.schedule.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping // Lv3. 작성자 생성
    public ResponseEntity<AuthorResponseDto> createAuthor(@RequestBody @Valid AuthorRequestDto dto) {
        return new ResponseEntity<>(authorService.saveAuthor(dto), HttpStatus.CREATED);
    }
}


