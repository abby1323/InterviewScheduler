package com.project.MockInterviewScheduler.service.interfaces;

import com.project.MockInterviewScheduler.entity.InterviewerFeedback;
import com.project.MockInterviewScheduler.entity.StudentFeedback;

public interface StudentFeedbackServiceInterface {

    StudentFeedback addFeedback(StudentFeedback feedback,Long interviewSessionId, Long userId);
    StudentFeedback getFeedbackByInterviewId(Long interviewSessionId,Long userId);

}
