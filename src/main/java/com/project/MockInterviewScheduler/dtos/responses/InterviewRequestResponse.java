package com.project.MockInterviewScheduler.dtos.responses;

import lombok.Data;

@Data
public class InterviewRequestResponse {
    private Long id;
    private Long studentId;
    private String status;
}
