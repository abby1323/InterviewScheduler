package com.project.MockInterviewScheduler.repository;

import com.project.MockInterviewScheduler.entity.InterviewSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InterviewSessionRepository extends JpaRepository<InterviewSession,Long> {
    // traverse: InterviewSession → match → student → id
    Optional<InterviewSession> findByMatchStudentId(Long studentId);

    // traverse: InterviewSession → match → interviewer → id
    Optional<InterviewSession> findByMatchInterviewerId(Long interviewerId);
}
