package com.project.MockInterviewScheduler.service;


import com.project.MockInterviewScheduler.dtos.responses.InterviewRequestResponse;
import com.project.MockInterviewScheduler.entity.*;
import com.project.MockInterviewScheduler.enums.InterviewRequestStatus;
import com.project.MockInterviewScheduler.exceptions.InvalidRequestException;
import com.project.MockInterviewScheduler.exceptions.ResourceNotFoundException;
import com.project.MockInterviewScheduler.repository.InterviewRequestRepository;
import com.project.MockInterviewScheduler.repository.MatchRepository;
import com.project.MockInterviewScheduler.repository.StudentRepository;
import com.project.MockInterviewScheduler.service.interfaces.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService implements StudentServiceInterface {

    private final StudentRepository studentRepository;
    private final ExpertiseServiceInterface expertiseService;
    private final SlotServiceInterface slotService;
    private final StudentFeedbackServiceInterface studentFeedbackService;
    private final InterviewerFeedbackServiceInterface interviewerFeedbackService;
    private final MatchRepository matchRepository;
    private final InterviewRequestRepository interviewRequestRepository;

    @Override
    public Student addStudent(Student student) {
        Student newStudent = new Student();
        newStudent.setName(student.getName());
        newStudent.setBranch(student.getBranch());
        newStudent.setCollege(student.getCollege());
        newStudent.setTargetRole(student.getTargetRole());

        return studentRepository.save(newStudent);
    }

    @Override
    public Student updateStudent(Student student, Long id) {
        Student newStudent = getStudentById(id);
        newStudent.setName(student.getName());
        newStudent.setBranch(student.getBranch());
        newStudent.setCollege(student.getCollege());
        newStudent.setTargetRole(student.getTargetRole());
        return studentRepository.save(newStudent);
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No such user exists"));
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        student.setActive(false);
        studentRepository.save(student);
    }



    @Override
    public Expertise addExpertise(Expertise expertise, Long userId) {
        return expertiseService.addExpertise(expertise,getStudentById(userId));
    }

    @Override
    public List<Expertise> getAllExpertiseById(Long userId) {
        return getStudentById(userId).getExpertise();
    }

    @Override
    public AvailabilitySlot addSlot(AvailabilitySlot slot, Long userId) {
        return slotService.addSlot(slot, getStudentById(userId));
    }

    @Override
    public AvailabilitySlot updateSlot(AvailabilitySlot slot, Long slotId, Long userId) {
        return slotService.updateSlot(slot,slotId,getStudentById(userId));
    }

    @Override
    public List<AvailabilitySlot> getAllAvailabilitySlotsById(Long userId) {
        return getStudentById(userId).getSlot();
    }

    @Override
    public boolean acceptInterview(CustomUser user, Long matchId, boolean isAccepted) {
        Match match = matchRepository.findById(matchId).orElseThrow();
        if(match.getStudent().getId().equals(user.getId())) {
            match.setHasStudentAccepted(true);
            matchRepository.save(match);
            return true;
        }else{
            throw new InvalidRequestException("No such interview is scheduled for this user");
        }
    }

    @Override
    public StudentFeedback addFeedback(StudentFeedback feedback, Long interviewSessionId, Long userId) {
        return studentFeedbackService.addFeedback(feedback, interviewSessionId, userId);
    }

    @Override
    public InterviewerFeedback getFeedbackForStudent(Long interviewSessionId,Long userId) {
        return interviewerFeedbackService.getFeedbackByInterviewId(interviewSessionId,userId);
    }

    @Override
    public InterviewRequest makeInterviewRequest(Long userId) {
        InterviewRequest request = new InterviewRequest();
        request.setStudent(studentRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found")));
        request.setStatus(InterviewRequestStatus.PENDING);
        return interviewRequestRepository.save(request);
    }

    @Override
    public void deleteInterviewRequest(Long id, Long userId) {
        Student student = getStudentById(userId);
        InterviewRequest request= student.getInterviewRequest()
                .stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElseThrow(()->new ResourceNotFoundException("No such request exists"));
         request.setStatus(InterviewRequestStatus.CANCELLED);
         interviewRequestRepository.save(request);
    }
}
