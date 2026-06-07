package com.project.MockInterviewScheduler.entity;

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
public class AvailabilitySlot {

    private DifficultyLevel difficultyLevel;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @Enumerated(value = EnumType.STRING)
    private SlotStatus status;
    @ManyToOne
    @JoinColumn(name = "custom_user_id")
    private CustomUser user;
    @OneToOne
    private Match match;

}
