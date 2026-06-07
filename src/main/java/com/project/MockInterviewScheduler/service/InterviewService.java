package com.project.MockInterviewScheduler.service;

import com.project.MockInterviewScheduler.entity.InterviewSession;
import com.project.MockInterviewScheduler.entity.Match;
import com.project.MockInterviewScheduler.enums.InterviewStatus;
import com.project.MockInterviewScheduler.repository.InterviewSessionRepository;
import com.project.MockInterviewScheduler.service.interfaces.InterviewServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class InterviewService implements InterviewServiceInterface {

    private final InterviewSessionRepository interviewSessionRepository;

    @Override
    public InterviewSession addInterview(Match match) {
        InterviewSession interview = new InterviewSession();
        interview.setMatch(match);
        interview.setStatus(InterviewStatus.SCHEDULED);
        interview.setScheduledAt(match.getSlot().getStartTime());
        return interviewSessionRepository.save(interview);
    }

    @Override
    public InterviewSession getInterview(Long interviewSessionId) {
        return interviewSessionRepository.findById(interviewSessionId).orElseThrow(()-> new RuntimeException("No such interview exists"));
    }
}
