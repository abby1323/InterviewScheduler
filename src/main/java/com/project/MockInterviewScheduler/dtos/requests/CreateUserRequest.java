package com.project.MockInterviewScheduler.dtos.requests;

import com.project.MockInterviewScheduler.entity.Role;
import lombok.Data;

@Data
public class CreateUserRequest {
    private String email;
    private String password;
    private Role role;
    private String name;
    // student fields
    private String college;
    private String branch;
    private String targetRole;
    // interviewer fields
    private String company;
    private double experienceInYears;
    private String broadExpertiseBranch;
}
