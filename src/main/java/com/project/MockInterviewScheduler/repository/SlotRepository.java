package com.project.MockInterviewScheduler.repository;

import com.project.MockInterviewScheduler.entity.AvailabilitySlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlotRepository extends JpaRepository<AvailabilitySlot,Long> {
}
