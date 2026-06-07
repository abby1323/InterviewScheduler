package com.project.MockInterviewScheduler.service.interfaces;

import com.project.MockInterviewScheduler.entity.Interviewer;
import com.project.MockInterviewScheduler.entity.InterviewerFeedback;

public interface InterviewerFeedbackServiceInterface {
    InterviewerFeedback getFeedbackByInterviewId(Long interviewSessionId);
    InterviewerFeedback addFeedback(InterviewerFeedback feedback, Long interviewSessionId, Interviewer interviewerById);
}
