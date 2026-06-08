package com.project.MockInterviewScheduler.service;

import com.project.MockInterviewScheduler.entity.AvailabilitySlot;
import com.project.MockInterviewScheduler.entity.Interviewer;
import com.project.MockInterviewScheduler.entity.Match;
import com.project.MockInterviewScheduler.entity.Student;
import com.project.MockInterviewScheduler.enums.MatchStatus;
import com.project.MockInterviewScheduler.exceptions.ResourceNotFoundException;
import com.project.MockInterviewScheduler.repository.MatchRepository;
import com.project.MockInterviewScheduler.service.interfaces.MatchServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MatchService implements MatchServiceInterface {

    private final MatchRepository matchRepository;

    @Override
    public Match addMatch(Student student, Interviewer interviewer, AvailabilitySlot slot) {
        Match match1 = new Match();
        match1.setStudent(student);
        match1.setInterviewer(interviewer);
        match1.setCreatedAt(LocalDateTime.now());
        match1.setSlot(slot);
        match1.setStatus(MatchStatus.FOUND);
        return matchRepository.save(match1);
    }

    @Override
    public Match addNoMatch(Student student) {
        Match match1 = new Match();
        match1.setStudent(student);
        match1.setInterviewer(null);
        match1.setCreatedAt(LocalDateTime.now());
        match1.setStatus(MatchStatus.NOT_FOUND);
        return matchRepository.save(match1);
    }

    @Override
    public Match getMatch(Long id){
        return matchRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No such match found"));
    }

    @Override
    public String buildMeetLink(Match match) {
        return "https://meet.google.com/placeholder-" + match.getId();
    }
}
