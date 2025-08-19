package com.example.kafka.producer;

import com.example.kafka.web.dto.Click;
import com.example.kafka.web.dto.Meta;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, Click> jsonTemplate;

    public CompletableFuture<Meta> sendClick(Click message) {
        return jsonTemplate.send("clicks.incoming", message)
                .thenApply(result -> {
                    RecordMetadata metadata = result.getRecordMetadata();
                    return new Meta(
                            metadata.topic(),
                            metadata.partition(),
                            metadata.offset()
                    );
                })
                .exceptionally(ex -> {
                    return new Meta("ERROR", -1, -1L);
                });
    }

}
