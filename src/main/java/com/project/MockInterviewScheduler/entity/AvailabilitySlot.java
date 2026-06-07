package com.project.MockInterviewScheduler.entity;

import com.project.MockInterviewScheduler.enums.SlotStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilitySlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @Enumerated(value = EnumType.STRING)
    private SlotStatus status;
    @ManyToOne
    @JoinColumn(name = "custom_user_id")
    private CustomUser user;
    @OneToOne
    private Match match;

    public boolean overlapsWith(AvailabilitySlot other){
        if(other==null || other.getStartTime()== null || other.endTime==null)
            return false;

        return this.startTime.isBefore(other.getEndTime()) &&
                other.getStartTime().isBefore(this.getEndTime());
    }

    @Override
    public boolean equals(Object o){
        if(this==o)
            return true;
        if(!(o instanceof AvailabilitySlot that) )
            return false;
        return Objects.equals(id,that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
