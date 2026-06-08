package com.project.MockInterviewScheduler.dtos.requests;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SlotRequest {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String difficultyLevel;
}
