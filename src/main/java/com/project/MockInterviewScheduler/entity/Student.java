package com.project.MockInterviewScheduler.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student extends CustomUser{

    private String name;
    private String college;
    // compared while matching
    private String branch;
    private String targetRole;

//    // inherited from user
//    private List<Expertise> expertiseList = new ArrayList<>();
//    // each student adds their availabilitySlots
//    //inherited from user
//    @OneToMany
//    private List<AvailabilitySlot> availabilitySlot = new ArrayList<>();


    // each student can create one request at a time
    @OneToOne
    private InterviewRequest interviewRequest;


//     each student creates one interview request
//    @OneToMany(mappedBy = "student_id")
//    private List<InterviewRequest> interviewRequests;


}
