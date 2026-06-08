package com.project.MockInterviewScheduler.dtos.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudentFeedbackResponse {
    private Long id;
    private double rating;
    private String comments;
    private LocalDateTime submittedAt;
}
