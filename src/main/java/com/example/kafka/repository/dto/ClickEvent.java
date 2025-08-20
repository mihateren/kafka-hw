package com.example.kafka.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@AllArgsConstructor
@Getter
@Setter
public class ClickEvent {

    private Long id;

    private Integer userId;

    private Integer page;

    private OffsetDateTime clickedAt;

}
