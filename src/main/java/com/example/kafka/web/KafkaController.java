package com.example.kafka.web;

import com.example.kafka.producer.KafkaProducer;
import com.example.kafka.web.dto.Click;
import com.example.kafka.web.dto.Meta;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/kafka")
@RequiredArgsConstructor
public class KafkaController {

    private final KafkaProducer kafkaService;

    @PostMapping("/send-click")
    public CompletableFuture<ResponseEntity<Meta>> sendClick(@RequestBody Click message) {
        return kafkaService.sendClick(message)
                .thenApply(meta -> ResponseEntity.ok(meta))
                .exceptionally(ex -> ResponseEntity.status(500).build());
    }

}
