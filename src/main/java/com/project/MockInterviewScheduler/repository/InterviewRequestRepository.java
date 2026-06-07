package com.project.MockInterviewScheduler.repository;

import com.project.MockInterviewScheduler.entity.InterviewRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InterviewRequestRepository extends JpaRepository<InterviewRequest,Long> {
    Optional<InterviewRequest> findByStudentId(Long studentId);
}
