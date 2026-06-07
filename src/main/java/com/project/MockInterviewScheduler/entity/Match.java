package com.project.MockInterviewScheduler.entity;

import com.project.MockInterviewScheduler.enums.MatchStatus;
import com.project.MockInterviewScheduler.enums.SlotStatus;
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
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "interviewer_id")
    private Interviewer interviewer;
    private LocalDateTime createdAt;
    @OneToOne(mappedBy = "match_id")
    private AvailabilitySlot slot;
    private MatchStatus status;
}
