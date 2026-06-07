package com.project.MockInterviewScheduler.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentFeedback { // feedback provided by student
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(mappedBy = "interview_session_id")
    private InterviewSession interviewSession;
    @OneToOne(mappedBy = "user_id")
    private Student student;
    private LocalDateTime submittedAt;
    private double rating;
    private String comments;
}
