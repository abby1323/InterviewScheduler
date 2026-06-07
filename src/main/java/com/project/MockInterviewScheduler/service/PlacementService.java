package com.project.MockInterviewScheduler.service;

import com.project.MockInterviewScheduler.entity.*;
import com.project.MockInterviewScheduler.enums.InterviewStatus;
import com.project.MockInterviewScheduler.enums.InterviewerStatus;
import com.project.MockInterviewScheduler.enums.MatchStatus;
import com.project.MockInterviewScheduler.enums.SlotStatus;
import com.project.MockInterviewScheduler.repository.InterviewerRepository;
import com.project.MockInterviewScheduler.service.interfaces.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PlacementService implements PCInterface {

    private final StudentServiceInterface studentService;
    private final InterviewerServiceInterface interviewerService;
    private final InterviewRequestServiceInterface interviewRequestService;
    private final MatchServiceInterface matchService;
    private final InterviewServiceInterface interviewService;
    private final InterviewerRepository interviewerRepository;
    private final EmailService emailService;

    private static final int SUBDOMAIN_MATCH_THRESHOLD = 3;

    @Override
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @Override
    public List<InterviewRequest> getAllPendingInterviewRequests() {
        return interviewRequestService.getAllPendingRequests();
    }

    @Override
    public List<Interviewer> getAllApprovalRequests() {
        return interviewerService.getAllPendingInterviewers();
    }

    @Override
    public boolean approveInterviewer(Long id) {
        Interviewer interviewer = interviewerService.getInterviewerById(id);
        boolean isApproved = false;
        if(interviewer.getExperienceInYears() > 3) {
            interviewer.setStatus(InterviewerStatus.APPROVED);
            isApproved=true;
        }else {
            interviewer.setStatus(InterviewerStatus.REJECTED);
        }
        interviewerRepository.save(interviewer);
        return isApproved;
    }

    @Override
    public Match matchRequest(Long requestId) {
        InterviewRequest request = interviewRequestService.getRequestById(requestId);
        Student student = request.getStudent();
        List<Expertise> studentExpertiseList = student.getExpertise();

        List<Interviewer> approvedInterviewers = interviewerService.getAllApprovedInterviewers();

        for (Interviewer interviewer : approvedInterviewers){
            List<Expertise> interviewerExpertiseList = interviewer.getExpertise();

            int matchingSubdomainCount = countMatchingSubdomains(studentExpertiseList,interviewerExpertiseList);

            if(matchingSubdomainCount > SUBDOMAIN_MATCH_THRESHOLD){
                AvailabilitySlot matchedSlot = findOverlappingSlots(student.getSlot(),interviewer.getSlot());
                if(matchedSlot!=null){
                    matchedSlot.setStatus(SlotStatus.BOOKED);
                    return matchService.addMatch(student,interviewer,matchedSlot);
                }
            }
        }
        return matchService.addNoMatch(student);
    }

    private AvailabilitySlot findOverlappingSlots(List<AvailabilitySlot> studentSlots, List<AvailabilitySlot> interviewerSlots) {
        for(AvailabilitySlot interviewerSlot : interviewerSlots){
            if(!SlotStatus.OPEN.equals(interviewerSlot.getStatus()))
                continue;
            for (AvailabilitySlot studentSlot : studentSlots){
                if(!SlotStatus.OPEN.equals(studentSlot.getStatus()))
                    continue;
                if(interviewerSlot.overlapsWith(studentSlot)){
                    return interviewerSlot;
                }
            }
        }
        return null;
    }

    private int countMatchingSubdomains(List<Expertise> studentExpertiseList, List<Expertise> interviewerExpertiseList) {
        Set<String> studentSubDomains = new HashSet<>();
        for (Expertise expertise : studentExpertiseList){
            if(expertise.getSubDomains()!=null){
                studentSubDomains.addAll(expertise.getSubDomains());
            }
        }
        int count = 0;
        for (Expertise expertise : interviewerExpertiseList){
            if(expertise.getSubDomains()!=null){
                for(String subDomain : expertise.getSubDomains()){
                    if(studentSubDomains.contains(subDomain)){
                        count++;
                    }
                }
            }
        }
        return count;
    }


    @Override
    public void sendAcceptanceRequest(Long id) {
        Match match = matchService.getMatch(id);
        emailService.sendAcceptanceRequest(match);
    }

    @Override
    public InterviewSession createInterview(Long id) {
        Match match = matchService.getMatch(id);
        if (match.getStatus().equals(MatchStatus.NOT_FOUND)) {
            throw new RuntimeException("Match has bot been found yet -- cannot create interview");
        }
        InterviewSession interview = null;
        if (match.isHasStudentAccepted() && match.isHasInterviewerAccepted()){
            interview = interviewService.addInterview(match);
            emailService.sendInterviewConfirmation(match);
        }else {
                throw new RuntimeException("Interview cannot be created right now");
            }
        return interview;
        }
}
