package com.example.kafka.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Meta {

    private String topic;

    private Integer partition;

    private Long offset;

}
