package com.project.MockInterviewScheduler.service;


import com.project.MockInterviewScheduler.dtos.responses.InterviewRequestResponse;
import com.project.MockInterviewScheduler.entity.*;
import com.project.MockInterviewScheduler.exceptions.InvalidRequestException;
import com.project.MockInterviewScheduler.exceptions.ResourceNotFoundException;
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
    private final InterviewRequestServiceInterface interviewRequestService;

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
        return interviewRequestService.addRequest(userId);
    }

    @Override
    public void deleteInterviewRequest(Long id, Long userId) {
        Student student = getStudentById(userId);
        if(student.getInterviewRequest() == null){
            throw new ResourceNotFoundException("No such request exists");
        }
        if(!student.getInterviewRequest().getId().equals(id)){
            throw new ResourceNotFoundException("No such request exists");
        }else {
            interviewRequestService.deleteInterviewRequest(id);
        }
    }


//    private final StudentRepository studentRepository;
//    private final InterviewRequestService interviewRequestService;
//    private final InterviewSessionService interviewSessionService;
//    private final AvailabiltySlotService slotService;
//    private final ModelMapper mapper;
//
//    /* once a student profile is created through jwt login, we just set information we
//    get from the user, nothing about interview request or sessions, those will be set
//    once the admin approves, and then they will be shown on the student profile.
//     */
//
//    // Student Profile Methods
//
//    public StudentProfileResponse getStudentProfile(Long id){
//        return convertToStudentResponse(getStudentEntity(id));
//    }
//
//    public StudentProfileResponse updateProfile(StudentProfileRequest request, Long id){
//        Student updatedStudent = updateStudentEntity(getStudentEntity(id),request);
//        return convertToStudentResponse(updatedStudent);
//    }
//
//    /* While creating Interview Request, we will set the status as PENDING,
//    and not set any Match. This status and the match will later be set by the
//    admin
//     */
//
//    // Interview Request Methods
//
//    public InterviewRequestResponse createInterviewRequest(Long studentId, CreateInterviewRequest createRequest) {
//        Student student = getStudentEntity(studentId);
//        if(student.getInterviewRequest()!=null)
//            throw new ResourceAlreadyException("interview request already created");
//        InterviewRequest interviewRequest = interviewRequestService.addInterviewRequest(createRequest,student);
//        student.setInterviewRequest(interviewRequest);
//        return convertInterviewRequestToResponse(interviewRequest);
//    }
//
//    public InterviewRequestResponse getInterviewRequest(Long studentId){
//        Student student = getStudentEntity(studentId);
//        return convertInterviewRequestToResponse(student.getInterviewRequest());
//    }
//
//    // Availability Slot Methods
//    public SlotResponse addAvailabilitySlot(Long id, SlotRequest slotRequest){
//        Student student = getStudentEntity(id);
//        if (student.getAvailabilitySlot()!=null)
//            throw new ResourceAlreadyException("Slot added already");
//        AvailabilitySlot availabilitySlot = slotService.addStudentAvailSlot(student,slotRequest);
//        return convertToSlotResponse(availabilitySlot);
//    }
//
//    public SlotResponse getAvailabiltySlots(Long id){
//        Student student = getStudentEntity(id);
//        StudentAvailSlot slot = student.getAvailabilitySlot();
//        return convertToSlotResponse(slot);
//    }
//
//    // cancel interview
//
//    public void cancelInterview(Long sessionId){
//        interviewSessionService.cancelInterview(sessionId);
//    }
//
//    // helper methods
//
//    private SlotResponse convertToSlotResponse(AvailabilitySlot availabilitySlot) {
//        return mapper.map(availabilitySlot,SlotResponse.class);
//    }
//
//    public  Student getStudentEntity(Long id){
//        //id coming from @AuthenticationPrincipal in Controller set in Security Context
//        return studentRepository.findById(id)
//                .orElseThrow(() -> new UsernameNotFoundException("Student not found"));
//    }
//
//    public InterviewRequestResponse convertInterviewRequestToResponse(InterviewRequest interviewRequest) {
//        return mapper.map(interviewRequest,InterviewRequestResponse.class);
//    }
//
//    private Student updateStudentEntity(Student student,StudentProfileRequest request) {
//        student.setName(request.getName());
//        student.setCollege(request.getCollege());
//        student.setBranch(request.getBranch());
//        student.setTargetRole(request.getTargetRole());
//        return studentRepository.save(student);
//    }
//
//    public StudentProfileResponse convertToStudentResponse(Student student){
//        return mapper.map(student,StudentProfileResponse.class);
//    }
//
//    public Long getStudentIdFromUser(CustomUser user){
//        Student student = studentRepository.findByUserId(user.getId())
//                .orElseThrow(() -> new UsernameNotFoundException("Not found"));
//        return student.getId();
//    }
//
//
//    public void updateRating(double rating, Long id) {
//        getStudentEntity(id).setOverallRating(rating);
//    }
}
