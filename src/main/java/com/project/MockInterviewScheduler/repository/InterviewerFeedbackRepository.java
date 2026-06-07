package com.project.MockInterviewScheduler.repository;

import com.project.MockInterviewScheduler.entity.InterviewerFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewerFeedbackRepository extends JpaRepository<InterviewerFeedback,Long> {
}
