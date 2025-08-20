package com.example.kafka.web;

import com.example.kafka.producer.KafkaProducer;
import com.example.kafka.service.ClickService;
import com.example.kafka.web.dto.Click;
import com.example.kafka.web.dto.Meta;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/kafka")
@RequiredArgsConstructor
public class KafkaController {

    private final KafkaProducer kafkaProducer;

    @PostMapping("/send-click")
    public CompletableFuture<ResponseEntity<Meta>> sendClick(@RequestBody Click message) {
        return kafkaProducer.sendClick(message)
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex -> ResponseEntity.status(500).build());
    }



}
