package com.example.schedule.repository;

import com.example.schedule.dto.response.Schedule2ResponseDto;
import com.example.schedule.dto.response.Schedule3ResponseDto;
import com.example.schedule.dto.response.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.entity.Schedule2;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // Lv1. 일정 생성
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("task", schedule.getTask());
        parameters.put("author_name", schedule.getAuthorName());
        parameters.put("password", schedule.getPassword());
        parameters.put("created_at", schedule.getCreatedAt());
        parameters.put("updated_at", schedule.getUpdatedAt());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        return new ScheduleResponseDto(key.longValue(), schedule.getTask(), schedule.getAuthorName(), schedule.getCreatedAt(), schedule.getUpdatedAt());
    }

    // Lv1. 전체 일정 조회
    public List<ScheduleResponseDto> findAllSchedules(String updatedAt, String authorName) {
        StringBuilder sql = new StringBuilder("SELECT id, task, author_name, created_at, updated_at FROM schedule ");
        List<String> conditions = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        if (updatedAt != null && !updatedAt.isEmpty()) {
            conditions.add("DATE(updated_at) = ?");
            params.add(updatedAt);
        }
        if (authorName != null && !authorName.isEmpty()) {
            conditions.add("author_name = ?");
            params.add(authorName);
        }

        if (!conditions.isEmpty()) {
            sql.append(" WHERE ").append(String.join(" AND ", conditions));
        }
        sql.append(" ORDER BY updated_at DESC");
        return jdbcTemplate.query(sql.toString(), params.toArray(), scheduleRowMapper());
    }

    @Override // Lv1. 선택 일정 조회
    public Optional<Schedule> findScheduleById(Long id) {
        List<Schedule> result = jdbcTemplate.query("SELECT * FROM schedule WHERE id = ?", scheduleRowMapperV2(), id);
        return result.stream().findAny();
    }

    @Override // Lv2. 선택 일정 수정
    public Schedule update(Schedule schedule) {
        String sql = "UPDATE schedule SET task = ?, author_name = ?, updated_at = ? WHERE id = ?";
        jdbcTemplate.update(sql, schedule.getTask(), schedule.getAuthorName(), schedule.getUpdatedAt(), schedule.getId());
        return findScheduleById(schedule.getId())
                .orElseThrow(() -> new RuntimeException("Schedule not found after update"));
    }

    @Override // Lv2. 선택 일정 삭제
    public int deleteSchedule(Long id) {
        return jdbcTemplate.update("DELETE FROM schedule WHERE id = ?", id);
    }

    @Override // Lv3. 일정 생성
    public Schedule2ResponseDto saveSchedule2(Schedule2 schedule2) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule2").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("task", schedule2.getTask());
        parameters.put("author_id", schedule2.getAuthorId());
        parameters.put("password", schedule2.getPassword());
        parameters.put("created_at", schedule2.getCreatedAt());
        parameters.put("updated_at", schedule2.getUpdatedAt());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        return new Schedule2ResponseDto(key.longValue(), schedule2.getTask(), schedule2.getAuthorId(), schedule2.getCreatedAt(), schedule2.getUpdatedAt());
    }

    @Override  // Lv3. 작성자별 일정 조회
    public List<Schedule2ResponseDto> findSchedulesByAuthorId(Long authorId) {
        StringBuilder sql = new StringBuilder("SELECT * FROM schedule2 s JOIN author a ON s.author_id = a.id");
        List<Object> params = new ArrayList<>();
        if (authorId != null) {
            sql.append(" WHERE s.author_id = ?");
            params.add(authorId);
        }
        sql.append(" ORDER BY s.updated_at DESC");
        return jdbcTemplate.query(sql.toString(), params.toArray(), scheduleRowMapperV3());
    }

    @Override // Lv4. 페이지네이션
    public List<Schedule3ResponseDto> getPagedSchedulesWithAuthor(int offset, int size) {
        String sql = "SELECT s.id, s.task, s.author_id, a.name as author_name, s.created_at, s.updated_at " +
                    "FROM schedule2 s JOIN author a ON s.author_id = a.id " +
                    "ORDER BY s.updated_at DESC LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, scheduleRowMapperV4(), size, offset);
    }

    // Lv1. 전체 일정 조회
    private RowMapper<ScheduleResponseDto> scheduleRowMapper() {
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getLong("id"),
                        rs.getString("task"),
                        rs.getString("author_name"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
            }
        };
    }

    // Lv1. 선택 일정 조회
    private RowMapper<Schedule> scheduleRowMapperV2() {
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
                        rs.getLong("id"),
                        rs.getString("task"),
                        rs.getString("author_name"),
                        rs.getString("password"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
            }
        };
    }

    // Lv3. 작성자별 일정 조회
    private RowMapper<Schedule2ResponseDto> scheduleRowMapperV3() {
        return new RowMapper<Schedule2ResponseDto>() {
            @Override
            public Schedule2ResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule2ResponseDto(
                        rs.getLong("id"),
                        rs.getString("task"),
                        rs.getLong("author_id"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
            }
        };
    }

    // Lv4. 페이지네이션
    private RowMapper<Schedule3ResponseDto> scheduleRowMapperV4() {
        return new RowMapper<Schedule3ResponseDto>() {
            @Override
            public Schedule3ResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule3ResponseDto(
                        rs.getLong("id"),
                        rs.getString("task"),
                        rs.getLong("author_id"),
                        rs.getString("author_name"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
            }
        };
    }

}