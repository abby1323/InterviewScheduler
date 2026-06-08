package com.project.MockInterviewScheduler.dtos.requests;

import lombok.Data;

@Data
public class StudentFeedbackRequest {
    private double rating;
    private String comments;
}
