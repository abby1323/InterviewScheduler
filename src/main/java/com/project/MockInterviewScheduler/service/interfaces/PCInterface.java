package com.project.MockInterviewScheduler.service.interfaces;

import com.project.MockInterviewScheduler.entity.*;

import java.util.List;

public interface PCInterface {

    public List<Student> getAllStudents();
    public List<InterviewRequest> getAllPendingInterviewRequests();
    public List<Interviewer> getAllApprovalRequests();
    public boolean approveInterviewer(Long id);
    public Match matchRequest(Long requestId);
    public void sendAcceptanceRequest(Long id);
    public InterviewSession createInterview(Long id);

    }


