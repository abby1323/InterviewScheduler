package com.project.MockInterviewScheduler.dtos.responses;

import lombok.Data;

@Data
public class InterviewerResponse {
    private Long id;
    private String name;
    private String company;
    private String broadExpertiseBranch;
    private double experienceInYears;
    private double overAllRating;
    private String status;

}
