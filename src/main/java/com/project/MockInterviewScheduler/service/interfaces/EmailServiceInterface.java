package com.project.MockInterviewScheduler.service.interfaces;

import com.project.MockInterviewScheduler.entity.Match;

public interface EmailServiceInterface {
    void sendAcceptanceRequest(Match match);

    void sendInterviewConfirmation(Match match);

    void sendFeedbackReminder(Match match);
}
