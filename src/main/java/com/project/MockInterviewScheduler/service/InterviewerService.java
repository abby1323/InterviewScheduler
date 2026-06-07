package com.project.MockInterviewScheduler.service;


import com.project.MockInterviewScheduler.entity.*;
import com.project.MockInterviewScheduler.enums.InterviewerStatus;
import com.project.MockInterviewScheduler.repository.InterviewerRepository;
import com.project.MockInterviewScheduler.service.interfaces.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InterviewerService implements InterviewerServiceInterface {

    private final InterviewerRepository interviewerRepository;
    private final ExpertiseServiceInterface expertiseService;
    private final SlotServiceInterface slotService;
    private final InterviewerFeedbackServiceInterface interviewerFeedbackService;
    private final StudentFeedbackServiceInterface studentFeedbackService;

    @Override
    public Interviewer addInterviewer(Interviewer interviewer) {
        Interviewer newInterviewer = new Interviewer();
        newInterviewer.setCompany(interviewer.getCompany());
        newInterviewer.setName(interviewer.getName());
        newInterviewer.setExperienceInYears(interviewer.getExperienceInYears());
        newInterviewer.setStatus(InterviewerStatus.PENDING);
        return interviewerRepository.save(newInterviewer);
    }

    @Override
    public Interviewer updateInterviewer(Interviewer interviewer, Long id) {
        Interviewer newInterviewer = getInterviewerById(id);
        newInterviewer.setCompany(interviewer.getCompany());
        newInterviewer.setName(interviewer.getName());
        newInterviewer.setExperienceInYears(interviewer.getExperienceInYears());
        return newInterviewer;
    }

    @Override
    public Interviewer getInterviewerById(Long id) {
        return interviewerRepository.findById(id).orElseThrow(() -> new RuntimeException("No such user exists"));
    }

    @Override
    public List<Interviewer> getAllInterviewers() {
        return interviewerRepository.findAll();
    }

    @Override
    public List<Interviewer> getAllPendingInterviewers(){
        return interviewerRepository.findAll()
                .stream()
                .filter(interviewer -> InterviewerStatus.PENDING.equals(interviewer.getStatus()))
                .toList();
    }

    @Override
    public void deleteInterviewer(Long id) {
        interviewerRepository.deleteById(id);
    }

    @Override
    public Expertise addExpertise(Expertise expertise, Long useId) {
        return expertiseService.addExpertise(expertise,getInterviewerById(useId));
    }

    @Override
    public List<Expertise> getAllExpertiseByUserId(Long userId) {
        return getInterviewerById(userId).getExpertise();
    }

    @Override
    public AvailabilitySlot addSlot(AvailabilitySlot slot, Long userId) {
        return slotService.addSlot(slot,getInterviewerById(userId));
    }

    @Override
    public AvailabilitySlot updateSLot(AvailabilitySlot slot, Long slotId, Long userId) {
        return slotService.updateSlot(slot,slotId,getInterviewerById(userId));
    }

    @Override
    public List<AvailabilitySlot> getAllAvailabilitySlotsByUserId(Long userId) {
        return getInterviewerById(userId).getSlot();
    }

    @Override
    public boolean acceptInterview(Long sessionId) {
        return false;
    }

    @Override
    public InterviewerFeedback addFeedback(InterviewerFeedback feedback, Long interviewSessionId, Long userId) {
        return interviewerFeedbackService.addFeedback(feedback,interviewSessionId,getInterviewerById(userId));
    }

    @Override
    public StudentFeedback getFeedbackForInterviewer(Long interviewSessionId) {
        return studentFeedbackService.getFeedbackByInterviewId(interviewSessionId);
    }

    @Override
    public List<Interviewer> getAllApprovedInterviewers() {
        return interviewerRepository.findAll()
                .stream()
                .filter(interviewer -> InterviewerStatus.APPROVED.equals(interviewer.getStatus()))
                .toList();
    }


//    private final InterviewerRepository interviewerRepository;
//    private final IntervieweAvailabiltySlotRepository slotRepository;
//    private final InterviewSessionService interviewSessionService;
//    private final ModelMapper mapper;
//
//    /* once a Interviewer profile is created through jwt login, we just set information we
//    get from the user, nothing about sessions, those will be set
//    once the admin approves, and then they will be shown on the student profile.
//     */
//
//    // Interviewer Profile Methods
//
//    public InterviewerProfileResponse getInterviewerProfile(Long id){
//        return convertToInterviewerResponse(getInterviewerEntity(id));
//    }
//
//    public InterviewerProfileResponse updateProfile(InterviewerProfileRequest request, Long id){
//        Interviewer updatedInterviewer = updateInterviewerEntity(getInterviewerEntity(id),request);
//        return convertToInterviewerResponse(updatedInterviewer);
//    }
//
//    // Availability Slot Methods
//
//    public SlotResponse addAvailabilitySlot(Long id, SlotRequest slotRequest){
//        Interviewer interviewer = getInterviewerEntity(id);
//        if (interviewer.getAvailabilitySlot()!=null)
//            throw new AlreadyExistsException("Slot added already");
//        AvailabilitySlot availabilitySlot = buildAvailabiltySlot(interviewer,slotRequest);
//        return convertToSlotResponse(availabilitySlot);
//    }
//
//    public SlotResponse getAvailabiltySlots(Long id){
//        Interviewer interviewer = getInterviewerEntity(id);
//        InterviewerAvailSlot slot = interviewer.getAvailabilitySlot();
//        return convertToSlotResponse(slot);
//    }
//
//
//    // helper methods
//    public void cancelInterview(Long sessionId){
//        interviewSessionService.cancelInterview(sessionId);
//    }
//
//
//    private SlotResponse convertToSlotResponse(AvailabilitySlot availabilitySlot) {
//        return mapper.map(availabilitySlot,SlotResponse.class);
//    }
//
//    private InterviewerAvailSlot buildAvailabiltySlot(Interviewer interviewer, SlotRequest slotRequest) {
//        InterviewerAvailSlot slot = new InterviewerAvailSlot();
//        slot.setInterviewer(interviewer);
//        slot.setStartTime(slotRequest.getStartTime());
//        slot.setEndTime(slotRequest.getEndTime());
//        slot.setStatus(SlotStatus.OPEN);
//        return slotRepository.save(slot);
//    }
//
//    public  Interviewer getInterviewerEntity(Long id){
//        //id coming from @AuthenticationPrincipal in Controller set in Security Context
//        return interviewerRepository.findById(id)
//                .orElseThrow(() -> new UsernameNotFoundException("Interviewer not found"));
//    }
//
//
//    private Interviewer updateInterviewerEntity(Interviewer interviewer,InterviewerProfileRequest request) {
//        interviewer.setName(request.getName());
//        interviewer.setCompany(request.getCompany());
//        interviewer.setExperienceDomains(request.getExperienceDomains());
//        interviewer.setExperienceInYears(request.getExperienceInYears());
//        interviewer.setBroadExpertiseBranch(request.getBroadExpertiseBranch());
//        return interviewerRepository.save(interviewer);
//    }
//
//    public InterviewerProfileResponse convertToInterviewerResponse(Interviewer interviewer){
//        return mapper.map(interviewer,InterviewerProfileResponse.class);
//    }
//
//    public Long getInterviewerIdFromUser(CustomUser user){
//        Interviewer interviewer = interviewerRepository.findByUserId(user.getId())
//                .orElseThrow(() -> new UsernameNotFoundException("Not found"));
//        return interviewer.getId();
//    }
//
//    public void updateRating(double rating, Long id) {
//        getInterviewerEntity(id).setOverallRating(rating);
//    }
//
//    public boolean approveInterviewer(Long interviewerId) {
//        Interviewer interviewer = getInterviewerEntity(interviewerId);
//        interviewer.setApproved(true);
//        return true;
//    }
//
//    public boolean rejectInterviewer(Long interviewerId) {
//        Interviewer interviewer = getInterviewerEntity(interviewerId);
//        interviewer.setApproved(false);
//        return false;
//    }
}
