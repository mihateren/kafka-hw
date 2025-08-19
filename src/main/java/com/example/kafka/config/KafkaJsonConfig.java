package com.example.kafka.config;

import com.example.kafka.web.dto.Click;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaJsonConfig {

    private final KafkaAppProperties kafkaAppProperties;

    @Bean
    public ProducerFactory<String, Click> jsonProducerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAppProperties.getBootstrapServers());
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.LINGER_MS_CONFIG, 5);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 32 * 1024);
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        if (kafkaAppProperties.getSchemaRegistryUrl() != null) {
            props.put("schema.registry.url", kafkaAppProperties.getSchemaRegistryUrl());
        }

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, Click> jsonKafkaTemplate(ProducerFactory<String, Click> jsonProducerFactory) {
        KafkaTemplate<String, Click> kafkaTemplate = new KafkaTemplate<>(jsonProducerFactory);
        kafkaTemplate.setDefaultTopic(kafkaAppProperties.getProducers().get("json").getTopic());
        return kafkaTemplate;
    }


    @Bean
    public ConsumerFactory<String, Click> jsonConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAppProperties.getBootstrapServers());

        String groupId = kafkaAppProperties.getConsumers().get("json").getGroupId();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId == null || groupId.isBlank() ? "undefined-group" : groupId);

        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaAppProperties.getConsumers().get("common").getAutoOffsetReset());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaAppProperties.getConsumers().get("common").isAutoCommit());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.example.kafka.*");

        if (kafkaAppProperties.getSchemaRegistryUrl() != null) {
            props.put("schema.registry.url", kafkaAppProperties.getSchemaRegistryUrl());
        }

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean(name = "jsonKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, Click> jsonKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Click> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(jsonConsumerFactory());
        factory.setConcurrency(3);

        return factory;
    }

}

