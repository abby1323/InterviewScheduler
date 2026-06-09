package com.project.MockInterviewScheduler.repository;

import com.project.MockInterviewScheduler.entity.InterviewRequest;
import com.project.MockInterviewScheduler.enums.InterviewRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterviewRequestRepository extends JpaRepository<InterviewRequest,Long> {

   List<InterviewRequest> findByStatus(InterviewRequestStatus interviewRequestStatus);

   Optional<InterviewRequest> findByStudentIdAndStatus(Long userId, InterviewRequestStatus interviewRequestStatus);
}
