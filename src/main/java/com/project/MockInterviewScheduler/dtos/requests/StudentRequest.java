package com.project.MockInterviewScheduler.dtos.requests;

import lombok.Data;

@Data
public class StudentRequest {
    private String name;
    private String branch;
    private String college;
    private String targetRole;
    private String email;
    private String password;
}
