package com.project.MockInterviewScheduler.repository;

import com.project.MockInterviewScheduler.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match,Long> {
}
