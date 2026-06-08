package com.project.MockInterviewScheduler.controller;

import com.project.MockInterviewScheduler.dtos.responses.*;
import com.project.MockInterviewScheduler.entity.*;
import com.project.MockInterviewScheduler.service.interfaces.EmailServiceInterface;
import com.project.MockInterviewScheduler.service.interfaces.InterviewServiceInterface;
import com.project.MockInterviewScheduler.service.interfaces.PCInterface;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/placement-admin")
public class PlacementController {

    private final PCInterface placementService;
    private final InterviewServiceInterface interviewService;
    private final EmailServiceInterface emailService;
    private final ModelMapper mapper;

    @GetMapping("/students/all")
    public ResponseEntity<?> getAllStudents() {
        List<Student> students = placementService.getAllStudents();
        List<StudentResponse> responses = students.stream()
                .map(this::getStudentResponse).toList();
        return ResponseEntity.ok(new ApiResponse("Success!", responses));
    }

    @GetMapping("/interview-requests/pending")
    public ResponseEntity<?> getAllPendingInterviewRequests() {
        List<InterviewRequest> requests = placementService.getAllPendingInterviewRequests();
        List<InterviewRequestResponse> responses = requests.stream()
                .map(this::getInterviewRequestResponse).toList();
        return ResponseEntity.ok(new ApiResponse("Success!", responses));
    }

    @GetMapping("/interviewers/pending")
    public ResponseEntity<?> getAllApprovalRequests() {
        List<Interviewer> interviewers = placementService.getAllApprovalRequests();
        List<InterviewerResponse> responses = interviewers.stream()
                .map(this::getInterviewerResponse).toList();
        return ResponseEntity.ok(new ApiResponse("Success!", responses));
    }

    @PostMapping("/approve-interviewer/{id}")
    public ResponseEntity<?> approveInterviewer(@PathVariable Long id) {
        boolean isApproved = placementService.approveInterviewer(id);
        return ResponseEntity.ok(new ApiResponse("Success", isApproved));
    }

    @PostMapping("/match-request/{id}")
    public ResponseEntity<?> matchRequest(@PathVariable Long id) {
        Match match = placementService.matchRequest(id);
        MatchResponse response = getMatchResponse(match);
        return ResponseEntity.ok(new ApiResponse("Success", response));
    }

    @PostMapping("/send-acceptance-request/{id}")
    public ResponseEntity<?> sendAcceptanceRequest(@PathVariable Long id) {
        placementService.sendAcceptanceRequest(id);
        return ResponseEntity.ok(new ApiResponse("Success", null));
    }

    @PostMapping("/create-interview/{id}")
    public ResponseEntity<?> createInterview(@PathVariable Long id) {
        InterviewSession interview = placementService.createInterview(id);
        InterviewResponse response = getInterviewResponse(interview);
        return ResponseEntity.ok(new ApiResponse("Success", response));
    }

    @PostMapping("/complete-interview/{id}")
    public ResponseEntity<?> completeInterview(@PathVariable Long id) {
        Match match = interviewService.markComplete(id);
        emailService.sendFeedbackReminder(match);
        return ResponseEntity.ok(new ApiResponse("Success", null));

    }

    private StudentResponse getStudentResponse(Student student) {
        return mapper.map(student, StudentResponse.class);
    }

    private InterviewerResponse getInterviewerResponse(Interviewer newInterviewer) {
        return mapper.map(newInterviewer, InterviewerResponse.class);
    }

    private MatchResponse getMatchResponse(Match match) {
        return mapper.map(match, MatchResponse.class);
    }

    private InterviewResponse getInterviewResponse(InterviewSession interviewSession) {
        return mapper.map(interviewSession, InterviewResponse.class);
    }

    private InterviewRequestResponse getInterviewRequestResponse(InterviewRequest request) {
        return mapper.map(request, InterviewRequestResponse.class);
    }
}