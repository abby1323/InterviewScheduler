package com.project.MockInterviewScheduler.dtos.requests;

import lombok.Data;

@Data
public class InterviewerRequest {
    private String name;
    private String company;
    private String broadExpertiseBranch;
    private double experienceInYears;
    private String email;
    private String password;

}
