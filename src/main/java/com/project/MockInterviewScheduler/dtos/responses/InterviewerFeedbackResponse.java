package com.project.MockInterviewScheduler.dtos.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InterviewerFeedbackResponse {
    private Long id;
    private String communication;
    private String strengths;
    private String improvements;
    private String recommendations;
    private LocalDateTime submittedAt;
}
