package com.project.MockInterviewScheduler.service.interfaces;

import com.project.MockInterviewScheduler.entity.*;

import java.util.List;

public interface InterviewerServiceInterface {

    Interviewer addInterviewer(Interviewer interviewer);
    Interviewer updateInterviewer(Interviewer interviewer,Long id);
    Interviewer getInterviewerById(Long id);
    List<Interviewer> getAllInterviewers();
    List<Interviewer> getAllPendingInterviewers();
    void deleteInterviewer(Long id);
    Expertise addExpertise(Expertise expertise, Long useId);
    List<Expertise> getAllExpertiseByUserId(Long userId);
    AvailabilitySlot addSlot(AvailabilitySlot slot, Long userId);
    AvailabilitySlot updateSLot(AvailabilitySlot slot, Long slotId, Long userId);
    List<AvailabilitySlot> getAllAvailabilitySlotsByUserId(Long userId);
    boolean acceptInterview(CustomUser user, Long matchId, boolean isAccepted);
    InterviewerFeedback addFeedback(InterviewerFeedback feedback, Long interviewSessionId, Long userId);
    StudentFeedback getFeedbackForInterviewer(Long interviewSessionId, Long userId);
    List<Interviewer> getAllApprovedInterviewers();
    void saveinterviewer(Interviewer interviewer);
}
