package com.project.MockInterviewScheduler.service.interfaces;

import com.project.MockInterviewScheduler.entity.InterviewRequest;

import java.util.List;

public interface InterviewRequestServiceInterface {

    public InterviewRequest addRequest(Long id);
    public InterviewRequest deleteInterviewRequest(Long id);
    public InterviewRequest getActiveRequestByUserId(Long userId);
    public List<InterviewRequest> getAllPendingRequests();
    InterviewRequest getRequestById(Long id);
}
