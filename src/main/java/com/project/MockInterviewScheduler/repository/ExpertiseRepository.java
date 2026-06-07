package com.project.MockInterviewScheduler.repository;

import com.project.MockInterviewScheduler.entity.Expertise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpertiseRepository extends JpaRepository<Expertise,Long> {
}
