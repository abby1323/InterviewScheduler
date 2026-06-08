package com.project.MockInterviewScheduler.service;

import com.project.MockInterviewScheduler.entity.InterviewSession;
import com.project.MockInterviewScheduler.entity.Interviewer;
import com.project.MockInterviewScheduler.entity.Student;
import com.project.MockInterviewScheduler.entity.StudentFeedback;
import com.project.MockInterviewScheduler.exceptions.ResourceNotFoundException;
import com.project.MockInterviewScheduler.exceptions.UnauthorizedActionException;
import com.project.MockInterviewScheduler.repository.InterviewerRepository;
import com.project.MockInterviewScheduler.repository.StudentFeedbackRepository;
import com.project.MockInterviewScheduler.repository.StudentRepository;
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
    private final InterviewServiceInterface interviewService;
    private final StudentRepository studentRepository;
    private final InterviewerRepository interviewerRepository;
    @Override
    public StudentFeedback addFeedback(StudentFeedback feedback, Long interviewSessionId, Long userId) {

        InterviewSession session = interviewService.getInterview(interviewSessionId);

        StudentFeedback feedback1 = new StudentFeedback();
        feedback1.setComments(feedback.getComments());
        feedback1.setRating(feedback.getRating());
        feedback1.setSubmittedAt(LocalDateTime.now());
        feedback1.setStudent(getStudentById(userId));
        feedback1.setInterviewSession(session);

        updateInterviewerRating(session,feedback.getRating());

        return studentFeedbackRepository.save(feedback1);
    }

    private void updateInterviewerRating(InterviewSession session, double rating) {
        Interviewer interviewer = session.getMatch().getInterviewer();
        double oldRating = interviewer.getOverallRating();
        int totalReviews = interviewer.getTotalReviews() ;

        double updatedRating = ((oldRating * totalReviews) + rating) / (totalReviews + 1);
        interviewer.setOverallRating(Math.round(updatedRating * 10.0) / 10.0);
        interviewer.setTotalReviews(totalReviews+1);

        interviewerRepository.save(interviewer);
    }

    private Student getStudentById(Long userId) {
        return studentRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("No such student exists"));
    }

    @Override
    public StudentFeedback getFeedbackByInterviewId(Long interviewSessionId, Long userId) {
        InterviewSession interview = interviewService.getInterview(interviewSessionId);
        if(!interview.getMatch().getInterviewer().getId().equals(userId)){
            throw new UnauthorizedActionException("User does not have access to this page");
        }
        return interview.getStudentFeedback();
    }
}
