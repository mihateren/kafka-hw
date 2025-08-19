package com.example.kafka.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
public class Click {

    @NotNull("userId обязателен")
    private String userId;

    @NotNull("page обязателен")
    private Integer page;

    private OffsetDateTime time = OffsetDateTime.now();

}
