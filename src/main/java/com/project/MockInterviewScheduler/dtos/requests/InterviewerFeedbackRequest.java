package com.project.MockInterviewScheduler.dtos.requests;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InterviewerFeedbackRequest {
    private String communication;
    private String strengths;
    private String improvements;
    private String recommendations;
}
