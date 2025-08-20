package com.example.kafka.service;

import com.example.kafka.repository.PostgresRepository;
import com.example.kafka.repository.dto.ClickEvent;
import com.example.kafka.web.dto.Click;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClickService {

    private final PostgresRepository repository;

    public Map<Integer, Long> getClicks(String fromStr, String toStr) {
        LocalDate from = LocalDate.parse(fromStr);
        LocalDate to = LocalDate.parse(toStr);

        // ыыы
        ZoneOffset moscowOffset = ZoneOffset.of("+03:00");
        OffsetDateTime fromDateTime = from.atStartOfDay().atOffset(moscowOffset);
        OffsetDateTime toDateTime = to.atStartOfDay().atOffset(moscowOffset);

        return repository.findAll(fromDateTime, toDateTime).stream()
                .collect(Collectors.groupingBy(
                        ClickEvent::getPage,
                        Collectors.counting()
                ));
    }

}
