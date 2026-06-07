package com.project.MockInterviewScheduler.repository;

import com.project.MockInterviewScheduler.entity.Interviewer;
import com.project.MockInterviewScheduler.entity.Student;
import com.project.MockInterviewScheduler.enums.InterviewerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterviewerRepository extends JpaRepository<Interviewer,Long> {
    List<Interviewer> findByStatus(InterviewerStatus status);
}
