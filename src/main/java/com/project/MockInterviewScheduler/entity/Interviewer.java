package com.project.MockInterviewScheduler.entity;

import com.project.MockInterviewScheduler.enums.InterviewerStatus;
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
public class Interviewer extends CustomUser{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String company;
    // compared while matching
    private String broadExpertiseBranch;
    private double experienceInYears;
    // compared while matching
    private List<String> experienceDomains;
    // computed from feedback
    private double overallRating;
    // approved by admin
    @Enumerated(value = EnumType.STRING)
    private InterviewerStatus status;


    //inherited from user class
//    // adds their available slot
//    @OneToMany
//    private AvailabilitySlot slot;
    // approval from admin
//    private boolean isApproved;


}
