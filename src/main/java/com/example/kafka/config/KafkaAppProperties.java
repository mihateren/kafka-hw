package com.example.kafka.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "kafka")
@Getter
@Setter
public class KafkaAppProperties {

    private String bootstrapServers;
    private String schemaRegistryUrl;

    private Map<String, ConsumerConfig> consumers;
    private Map<String, ProducerConfig> producers;

    @Getter
    @Setter
    public static class ConsumerConfig {
        private String autoOffsetReset;
        private boolean autoCommit;
        private String topic;
        private String groupId;
    }

    @Getter
    @Setter
    public static class ProducerConfig {
        private String topic;
    }
}
