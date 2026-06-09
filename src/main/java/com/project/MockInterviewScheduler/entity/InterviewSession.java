package com.project.MockInterviewScheduler.entity;

import com.project.MockInterviewScheduler.enums.InterviewStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InterviewSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "match_id")
    private Match match;
    private LocalDateTime scheduledAt;
    private InterviewStatus status;
    private String meetLink;

    @OneToOne(mappedBy = "interviewSession")
    private StudentFeedback studentFeedback;

    @OneToOne(mappedBy = "interviewSession")
    private InterviewerFeedback interviewerFeedback;

}
