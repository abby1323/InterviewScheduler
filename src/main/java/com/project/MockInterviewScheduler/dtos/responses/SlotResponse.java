package com.project.MockInterviewScheduler.dtos.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SlotResponse {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private String difficultyLevel;
}
