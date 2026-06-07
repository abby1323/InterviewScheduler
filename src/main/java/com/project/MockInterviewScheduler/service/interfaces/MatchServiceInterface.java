package com.project.MockInterviewScheduler.service.interfaces;

import com.project.MockInterviewScheduler.entity.AvailabilitySlot;
import com.project.MockInterviewScheduler.entity.Interviewer;
import com.project.MockInterviewScheduler.entity.Match;
import com.project.MockInterviewScheduler.entity.Student;

public interface MatchServiceInterface {

    Match addMatch(Student student, Interviewer interviewer, AvailabilitySlot slot);

    Match addNoMatch(Student student);

    Match getMatch(Long id);
}
