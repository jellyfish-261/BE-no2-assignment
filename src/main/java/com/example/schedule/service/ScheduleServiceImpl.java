package com.example.schedule.service;

import com.example.schedule.dto.request.Schedule2RequestDto;
import com.example.schedule.dto.request.ScheduleDeleteRequestDto;
import com.example.schedule.dto.request.ScheduleRequestDto;
import com.example.schedule.dto.request.ScheduleUpdateRequestDto;
import com.example.schedule.dto.response.Schedule2ResponseDto;
import com.example.schedule.dto.response.Schedule3ResponseDto;
import com.example.schedule.dto.response.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.entity.Schedule2;
import com.example.schedule.exception.PasswordMismatchException;
import com.example.schedule.exception.ScheduleNotFoundException;
import com.example.schedule.model.Paging;
import com.example.schedule.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService{

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override // Lv1. 일정 생성
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {
        Schedule schedule = new Schedule(dto.getTask(), dto.getAuthorName(), dto.getPassword());
        return scheduleRepository.saveSchedule(schedule);
    }

    @Override // Lv1. 전체 일정 조회
    public List<ScheduleResponseDto> findAllSchedules(String updatedAt, String authorName) {
        List<ScheduleResponseDto> schedules = scheduleRepository.findAllSchedules(updatedAt, authorName);
        if (schedules.isEmpty()) {
            throw new ScheduleNotFoundException("해당 조건에 맞는 일정이 존재하지 않습니다.");
        }
        return schedules;
    }

    @Override // Lv1. 선택 일정 조회
    public ScheduleResponseDto findScheduleById(Long id) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findScheduleById(id);
        if (optionalSchedule.isEmpty()) {
            throw new ScheduleNotFoundException("해당 ID(" + id + ")에 해당하는 일정이 존재하지 않습니다.");
        }
        return new ScheduleResponseDto(optionalSchedule.get());
    }

    @Transactional
    @Override // Lv2. 선택 일정 수정
    public ScheduleResponseDto updateSchedule(Long id, ScheduleUpdateRequestDto dto) {
        // 일정 조회 및 비밀번호 확인
        Schedule schedule = scheduleRepository.findScheduleById(id).orElseThrow(() -> new ScheduleNotFoundException("해당 ID(" + id + ")에 해당하는 일정이 존재하지 않습니다."));
        if (!schedule.getPassword().equals(dto.getPassword())) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }
        // 할일, 작성자명 수정
        if (dto.getTask() != null) {
            schedule.updateTask(dto.getTask());
        }
        if (dto.getAuthorName() != null) {
            schedule.updateAuthorName(dto.getAuthorName());
        }
        // 수정 시간 갱신 & DB에 저장
        schedule.updateUpdatedAt(LocalDateTime.now());
        Schedule updatedSchedule = scheduleRepository.update(schedule);
        return new ScheduleResponseDto(updatedSchedule);
    }

    @Override // Lv2. 선택 일정 삭제
    public void deleteSchedule(Long id, ScheduleDeleteRequestDto dto) {
        // 비밀번호 확인
        Schedule schedule = scheduleRepository.findScheduleById(id).orElseThrow(() -> new ScheduleNotFoundException("해당 ID(" + id + ")에 해당하는 일정이 존재하지 않습니다."));
        if (!schedule.getPassword().equals(dto.getPassword())) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }
        // 일정 삭제
        int deletedRow = scheduleRepository.deleteSchedule(id);
        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
    }

    @Override  // Lv3. 일정 생성
    public Schedule2ResponseDto saveSchedule2(Schedule2RequestDto dto) {
        Schedule2 schedule2 = new Schedule2(dto.getTask(), dto.getAuthorId(), dto.getPassword());
        return scheduleRepository.saveSchedule2(schedule2);
    }

    @Override  // Lv3. 작성자별 일정 조회
    public List<Schedule2ResponseDto> findSchedulesByAuthorId(Long authorId) {
        List<Schedule2ResponseDto> schedules = scheduleRepository.findSchedulesByAuthorId(authorId);
        if (schedules.isEmpty()) {
            throw new ScheduleNotFoundException("해당 조건에 맞는 일정이 존재하지 않습니다.");
        }
        return schedules;
    }

    @Override // Lv4. 페이지네이션
    public List<Schedule3ResponseDto> getPagedSchedulesWithAuthor(Paging paging) {
        int offset = (paging.getPage() - 1) * paging.getSize();
        List<Schedule3ResponseDto> schedules = scheduleRepository.getPagedSchedulesWithAuthor(offset, paging.getSize());
        if (schedules.isEmpty()) {
            throw new ScheduleNotFoundException("해당 조건에 맞는 일정이 존재하지 않습니다.");
        }
        return schedules;
    }

}
