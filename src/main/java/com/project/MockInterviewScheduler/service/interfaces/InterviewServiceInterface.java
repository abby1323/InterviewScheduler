package com.project.MockInterviewScheduler.service.interfaces;

import com.project.MockInterviewScheduler.entity.InterviewSession;
import com.project.MockInterviewScheduler.entity.Match;

public interface InterviewServiceInterface {
    public InterviewSession addInterview(Match match);

    InterviewSession getInterview(Long interviewSessionId);
}
