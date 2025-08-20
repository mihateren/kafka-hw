package com.example.kafka.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Click {

    @NotNull("userId обязателен")
    private Integer userId;

    @NotNull("page обязателен")
    private Integer page;

    private OffsetDateTime time = OffsetDateTime.now();

}
