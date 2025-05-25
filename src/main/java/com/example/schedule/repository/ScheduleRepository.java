package com.example.schedule.repository;

import com.example.schedule.dto.response.Schedule2ResponseDto;
import com.example.schedule.dto.response.Schedule3ResponseDto;
import com.example.schedule.dto.response.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.entity.Schedule2;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    ScheduleResponseDto saveSchedule(Schedule schedule); // Lv1. 일정 생성
    public List<ScheduleResponseDto> findAllSchedules(String updatedAt, String authorName); // Lv1. 전체 일정 조회
    Optional<Schedule> findScheduleById(Long id); // Lv1. 선택 일정 조회
    Schedule update(Schedule schedule); // Lv2. 선택 일정 수정
    int deleteSchedule(Long id); // Lv2. 선택 일정 삭제
    Schedule2ResponseDto saveSchedule2(Schedule2 schedule2); // Lv3. 일정 생성
    List<Schedule2ResponseDto> findSchedulesByAuthorId(Long authorId);  // Lv3. 작성자별 일정 조회
    List<Schedule3ResponseDto> getPagedSchedulesWithAuthor(int offset, int size); // Lv4. 페이지네이션
}
