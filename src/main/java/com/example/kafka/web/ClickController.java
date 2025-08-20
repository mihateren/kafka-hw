package com.example.kafka.web;

import com.example.kafka.service.ClickService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ClickController {

    private final ClickService clickService;

    @GetMapping("/stats")
    public ResponseEntity<Map<Integer, Long>> getClicks(
            @RequestParam("from") String from,
            @RequestParam("to") String to
    ) {
        return ResponseEntity.ok(clickService.getClicks(from, to));
    }

}
