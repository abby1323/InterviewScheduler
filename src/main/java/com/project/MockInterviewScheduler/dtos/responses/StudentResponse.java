package com.project.MockInterviewScheduler.dtos.responses;

import lombok.Data;

@Data
public class StudentResponse {
    private Long id;
    private String name;
    private String college;
    private String branch;
    private String targetRole;
}
