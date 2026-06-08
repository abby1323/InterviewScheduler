package com.project.MockInterviewScheduler.dtos.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MatchResponse {
    private Long id;
    private Long studentId;
    private Long interviewerId;
    private Long slotId;
    private String status;
    private LocalDateTime createdAt;
}
