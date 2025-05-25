package com.example.schedule.service;

import com.example.schedule.dto.request.Schedule2RequestDto;
import com.example.schedule.dto.request.ScheduleDeleteRequestDto;
import com.example.schedule.dto.request.ScheduleRequestDto;
import com.example.schedule.dto.response.Schedule2ResponseDto;
import com.example.schedule.dto.response.Schedule3ResponseDto;
import com.example.schedule.dto.response.ScheduleResponseDto;
import com.example.schedule.model.Paging;

import java.util.List;

public interface ScheduleService {
    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto); // Lv1. 일정 생성
    List<ScheduleResponseDto> findAllSchedules(String updatedAt, String authorName); // Lv1. 전체 일정 조회
    ScheduleResponseDto findScheduleById(Long id); // Lv1. 선택 일정 조회
    ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto dto); // Lv2. 선택 일정 수정
    void deleteSchedule(Long id, ScheduleDeleteRequestDto dto); // Lv2. 선택 일정 삭제
    Schedule2ResponseDto saveSchedule2(Schedule2RequestDto dto); // Lv3. 일정 생성
    List<Schedule2ResponseDto> findSchedulesByAuthorId(Long authorId);  // Lv3. 작성자별 일정 조회
    List<Schedule3ResponseDto> getPagedSchedulesWithAuthor(Paging paging); // Lv4. 페이지네이션
}
