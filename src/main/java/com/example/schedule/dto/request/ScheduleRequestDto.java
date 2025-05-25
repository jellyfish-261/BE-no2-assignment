package com.example.schedule.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    @NotBlank(message = "할 일은 필수 입력값입니다.")
    @Size(max = 200, message = "할일은 200자 이하만 입력 가능합니다.")
    private String task;

    private String authorName;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String password;
}
