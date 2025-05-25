package com.example.schedule.controller;

import com.example.schedule.dto.request.Schedule2RequestDto;
import com.example.schedule.dto.request.ScheduleDeleteRequestDto;
import com.example.schedule.dto.request.ScheduleRequestDto;
import com.example.schedule.dto.response.Schedule2ResponseDto;
import com.example.schedule.dto.response.Schedule3ResponseDto;
import com.example.schedule.dto.response.ScheduleResponseDto;
import com.example.schedule.model.Paging;
import com.example.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping // Lv1. 일정 생성
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody @Valid ScheduleRequestDto dto) {
        return new ResponseEntity<>(scheduleService.saveSchedule(dto), HttpStatus.CREATED);
    }

    @GetMapping // Lv1. 전체 일정 조회
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules(
            @RequestParam(required = false) String updatedAt,
            @RequestParam(required = false) String authorName) {
            List<ScheduleResponseDto> scheduleResponseDtoList = scheduleService.findAllSchedules(updatedAt, authorName);
            return new ResponseEntity<>(scheduleResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("{id}") // Lv1. 선택 일정 조회
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long id) {
        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }

    @PatchMapping("{id}") // Lv2. 선택 일정 수정
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody @Valid ScheduleRequestDto dto) {
        return new ResponseEntity<>(scheduleService.updateSchedule(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("{id}") // Lv2. 선택 일정 삭제
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long id,
            @RequestBody @Valid ScheduleDeleteRequestDto dto) {
        scheduleService.deleteSchedule(id, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/lv3") // Lv3. 일정 생성
    public ResponseEntity<Schedule2ResponseDto> createSchedule2(@RequestBody @Valid Schedule2RequestDto dto) {
        return new ResponseEntity<>(scheduleService.saveSchedule2(dto), HttpStatus.CREATED);
    }

    @GetMapping("/lv3/{authorId}") // Lv3. 작성자별 일정 조회
    public ResponseEntity<List<Schedule2ResponseDto>> findSchedulesByAuthorId(@PathVariable Long authorId) {
        List<Schedule2ResponseDto> schedules = scheduleService.findSchedulesByAuthorId(authorId);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    @GetMapping("/lv4") // Lv4. 페이지네이션
    public ResponseEntity<List<Schedule3ResponseDto>> getPagedSchedules(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Paging paging = new Paging(page, size);
        List<Schedule3ResponseDto> result = scheduleService.getPagedSchedulesWithAuthor(paging);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
