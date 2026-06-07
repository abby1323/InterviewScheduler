package com.project.MockInterviewScheduler.repository;

import com.project.MockInterviewScheduler.entity.StudentFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentFeedbackRepository extends JpaRepository<StudentFeedback,Long> {
}
