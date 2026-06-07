package com.project.MockInterviewScheduler.repository;

import com.project.MockInterviewScheduler.entity.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomUserRepository extends JpaRepository<CustomUser,Long> {

    Optional<CustomUser> findByEmail(String email);

}
