package com.project.MockInterviewScheduler.service;

import com.project.MockInterviewScheduler.entity.InterviewSession;
import com.project.MockInterviewScheduler.entity.StudentFeedback;
import com.project.MockInterviewScheduler.repository.StudentFeedbackRepository;
import com.project.MockInterviewScheduler.service.interfaces.InterviewServiceInterface;
import com.project.MockInterviewScheduler.service.interfaces.StudentFeedbackServiceInterface;
import com.project.MockInterviewScheduler.service.interfaces.StudentServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StudentFeedbackService implements StudentFeedbackServiceInterface {

    private final StudentFeedbackRepository studentFeedbackRepository;
    private final StudentServiceInterface studentService;
    private final InterviewServiceInterface interviewService;
    @Override
    public StudentFeedback addFeedback(StudentFeedback feedback, Long interviewSessionId, Long userId) {
        StudentFeedback feedback1 = new StudentFeedback();
        feedback1.setComments(feedback1.getComments());
        feedback1.setRating(feedback1.getRating());
        feedback1.setSubmittedAt(LocalDateTime.now());
        feedback1.setStudent(studentService.getStudentById(userId));
        feedback1.setInterviewSession(interviewService.getInterview(interviewSessionId));
        return studentFeedbackRepository.save(feedback1);
    }

    @Override
    public StudentFeedback getFeedbackByInterviewId(Long interviewSessionId) {
        InterviewSession interview = interviewService.getInterview(interviewSessionId);
        return interview.getStudentFeedback();
    }
}
