package com.project.MockInterviewScheduler.service.interfaces;

import com.project.MockInterviewScheduler.dtos.responses.InterviewRequestResponse;
import com.project.MockInterviewScheduler.entity.*;

import java.util.List;

public interface StudentServiceInterface {

    /* CRUD operations on student entity
    CRUD operations on Expertise
    CRUD operations on Availability Slots
    create or delete only one Interview Request
    Accept or Reject Interview Session
    Post feedback, view feedback
    *  */

    Student addStudent(Student student);
    Student updateStudent(Student student, Long id);
    Student getStudentById(Long id);
    List<Student> getAllStudents();
    void deleteStudent(Long id);
    Expertise addExpertise(Expertise expertise, Long id);
    List<Expertise> getAllExpertiseById(Long userId);
    AvailabilitySlot addSlot(AvailabilitySlot slot, Long userId);
    AvailabilitySlot updateSlot(AvailabilitySlot slot, Long slotId, Long userId);
    List<AvailabilitySlot> getAllAvailabilitySlotsById(Long userId);
    boolean acceptInterview(Long interviewSessionId, boolean isAccepted);

    StudentFeedback addFeedback(StudentFeedback feedback, Long interviewSessionId, Long userId);
    InterviewerFeedback getFeedbackForStudent(Long interviewSessionId);

    InterviewRequest makeInterviewRequest(Long userId);

    void deleteInterviewRequest(Long id);
}
