package com.project.MockInterviewScheduler.repository;

import com.project.MockInterviewScheduler.entity.InterviewSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InterviewSessionRepository extends JpaRepository<InterviewSession,Long> {
    Optional<InterviewSession> findByStudentId(Long id);
}
