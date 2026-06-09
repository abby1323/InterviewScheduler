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
public class InterviewerFeedback { //feedback given by interviewer
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "interview_session_id")
    private InterviewSession interviewSession;
    @OneToOne
    @JoinColumn(name = "interviewer_id")
    private Interviewer interviewer;
    private String communication;
    private String strengths;
    private String improvements;
    private String recommendations;
    private LocalDateTime submittedAt;
}
