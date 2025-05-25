package com.example.schedule.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AuthorRequestDto {
    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String name;

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;
}
