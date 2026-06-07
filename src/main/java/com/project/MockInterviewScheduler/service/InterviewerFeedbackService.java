package com.project.MockInterviewScheduler.service;

import com.project.MockInterviewScheduler.entity.InterviewSession;
import com.project.MockInterviewScheduler.entity.Interviewer;
import com.project.MockInterviewScheduler.entity.InterviewerFeedback;
import com.project.MockInterviewScheduler.entity.StudentFeedback;
import com.project.MockInterviewScheduler.repository.InterviewerFeedbackRepository;
import com.project.MockInterviewScheduler.service.interfaces.InterviewServiceInterface;
import com.project.MockInterviewScheduler.service.interfaces.InterviewerFeedbackServiceInterface;
import com.project.MockInterviewScheduler.service.interfaces.InterviewerServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InterviewerFeedbackService implements InterviewerFeedbackServiceInterface {

    private final InterviewServiceInterface interviewService;
    private final InterviewerFeedbackRepository interviewerFeedbackRepository;
    @Override
    public InterviewerFeedback getFeedbackByInterviewId(Long interviewSessionId) {
        InterviewSession interview = interviewService.getInterview(interviewSessionId);
        return interview.getInterviewerFeedback();
    }

    @Override
    public InterviewerFeedback addFeedback(InterviewerFeedback feedback, Long interviewSessionId, Interviewer interviewer) {
        InterviewerFeedback feedback1 = new InterviewerFeedback();
        feedback1.setInterviewSession(interviewService.getInterview(interviewSessionId));
        feedback1.setInterviewer(interviewer);
        feedback1.setCommunication(feedback.getCommunication());
        feedback1.setImprovements(feedback.getImprovements());
        feedback1.setRecommendations(feedback.getRecommendations());
        feedback1.setStrengths(feedback.getStrengths());
        return interviewerFeedbackRepository.save(feedback1);
    }
}
