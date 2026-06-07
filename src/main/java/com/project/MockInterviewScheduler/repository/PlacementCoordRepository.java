package com.project.MockInterviewScheduler.repository;

import com.project.MockInterviewScheduler.entity.PlacementCoordinator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlacementCoordRepository extends JpaRepository<PlacementCoordinator,Long> {
    Optional<PlacementCoordinator> findByUserId(Long userId);
}
