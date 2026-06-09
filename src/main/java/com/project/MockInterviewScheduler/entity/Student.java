package com.project.MockInterviewScheduler.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student extends CustomUser{

    private String name;
    private String college;
    private String branch;
    private String targetRole;

    // each student can create one request at a time
    @OneToMany(mappedBy = "student")
    private List<InterviewRequest> interviewRequest;



}
