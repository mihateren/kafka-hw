package com.example.kafka.consumer;

import com.example.kafka.repository.PostgresRepository;
import com.example.kafka.web.dto.Click;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final PostgresRepository postgresRepository;

    @KafkaListener(
            topics = "clicks.incoming",
            groupId = "clicks-group",
            containerFactory = "jsonKafkaListenerContainerFactory"
    )
    public void listen(Click message) {
        log.info("Received message: {}", message);

    }

}
