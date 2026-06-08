package com.project.MockInterviewScheduler.dtos.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InterviewResponse {
    private Long id;
    private Long matchId;
    private LocalDateTime scheduledAt;
    private String meetLink;
    private String status;
}
