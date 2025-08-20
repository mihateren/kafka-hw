package com.example.kafka.repository;

import com.example.kafka.repository.dto.ClickEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PostgresRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<ClickEvent> findAll(OffsetDateTime from, OffsetDateTime to) {
        String sql = "SELECT * FROM analytics.clicks WHERE clicked_at BETWEEN ? AND ?";
        log.info("SQL: {}, from: {}, to: {}", sql, from, to);
        var res = jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            return new ClickEvent(
                    resultSet.getLong("id"),
                    resultSet.getInt("user_id"),
                    resultSet.getInt("page"),
                    resultSet.getObject("clicked_at", OffsetDateTime.class)
            );
        }, from, to);
        log.info("Found {} clicks:", res.size());
        res.forEach(c -> log.info("Click: {}", c));
        return res;
    }

    public void save(ClickEvent click) {
        String sql = "INSERT INTO analytics.clicks (user_id, page, clicked_at) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, click.getUserId(), click.getPage(), click.getClickedAt());
    }

}
