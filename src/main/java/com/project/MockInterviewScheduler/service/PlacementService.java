package com.project.MockInterviewScheduler.service;

import com.project.MockInterviewScheduler.entity.*;
import com.project.MockInterviewScheduler.service.interfaces.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlacementService implements PCInterface {

    private final StudentServiceInterface studentService;
    private final InterviewerServiceInterface interviewerService;
    private final InterviewRequestServiceInterface interviewRequestService;
    private final MatchServiceInterface matchService;
    private final InterviewServiceInterface interviewService;

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
        return interviewer.getExperienceInYears() > 3;
    }

    @Override
    public Match matchRequest(Long requestId) {
        InterviewRequest request = interviewRequestService.getRequestById(requestId);
        Student student = request.getStudent();
        List<Expertise> studentExpertiseList = student.getExpertise();

        List<Interviewer> interviewers = interviewerService.getAllApprovedInterviewers();

        for(Interviewer interviewer : interviewers){
            List<Expertise> interviewerExpertiseList = interviewer.getExpertise();
            int count = 0;
            for(Expertise expertise : interviewerExpertiseList){
                if(studentExpertiseList.contains(expertise)){
                    count++;
                }
            }
            if(count>3){
                AvailabilitySlot matchedSlot = null;
                List<AvailabilitySlot> interviewerSlot = interviewer.getSlot();
                for(AvailabilitySlot slot : interviewerSlot){
                    if (student.getSlot().contains(slot)){
                        matchedSlot=slot;
                        break;
                    }
                }
                if(matchedSlot!=null){
                    return matchService.addMatch(student,interviewer,matchedSlot);
                }
            }

            }

        return matchService.addNoMatch(student);
        }



    @Override
    public void sendAcceptanceRequest(Long id) {
        // send request to match.student.email
        // send request to match.interviewer.email
    }

    @Override
    public InterviewSession createInterview(Long id) {
        Match match = matchService.getMatch(id);
        InterviewSession interview = null;
        if(studentService.acceptInterview(match.getStudent().getId()) && interviewerService.acceptInterview(match.getInterviewer().getId()))
            interview = interviewService.addInterview(match);
        return interview;
    }
}
