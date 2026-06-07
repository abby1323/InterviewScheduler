package com.project.MockInterviewScheduler.controller;


import com.project.MockInterviewScheduler.dtos.ApiResponse;
import com.project.MockInterviewScheduler.entity.*;
import com.project.MockInterviewScheduler.service.PlacementService;
import com.project.MockInterviewScheduler.service.interfaces.EmailServiceInterface;
import com.project.MockInterviewScheduler.service.interfaces.InterviewServiceInterface;
import com.project.MockInterviewScheduler.service.interfaces.PCInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("/placement-admin")
public class PlacementController  extends CustomUser{

    private final PCInterface placementService;
    private final InterviewServiceInterface interviewService;
    private final EmailServiceInterface emailService;

    @GetMapping("/students/all")
    public ResponseEntity<?> getAllStudents() {
        try {
            List<Student> students = placementService.getAllStudents();
            return ResponseEntity.ok(new ApiResponse("Success!", students));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/interview-requests/pending")
    public ResponseEntity<?> getAllPendingInterviewRequests() {
        try {
            List<InterviewRequest> requests = placementService.getAllPendingInterviewRequests();
            return ResponseEntity.ok(new ApiResponse("Success!", requests));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/interviewers/pending")
    public ResponseEntity<?> getAllApprovalRequests() {
        try {
            List<Interviewer> interviewers = placementService.getAllApprovalRequests();
            return ResponseEntity.ok(new ApiResponse("Success!", interviewers));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/approve-interviewer/{id}")
    public ResponseEntity<?> approveInterviewer(@PathVariable Long id) {
        try {
            boolean isApproved = placementService.approveInterviewer(id);
            return ResponseEntity.ok(new ApiResponse("Success", isApproved));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/match-request/{id}")
    public ResponseEntity<?> matchRequest(@PathVariable Long id) {
        try {
            Match match = placementService.matchRequest(id);
            return ResponseEntity.ok(new ApiResponse("Success", match));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/send-acceptance-request/{id}")
    public ResponseEntity<?> sendAcceptanceRequest(@PathVariable Long id) {
        try {
            placementService.sendAcceptanceRequest(id);
            return ResponseEntity.ok(new ApiResponse("Success", null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/create-interview/{id}")
    public ResponseEntity<?> createInterview(@PathVariable Long id) {
        try {
            InterviewSession interview  = placementService.createInterview(id);
            return ResponseEntity.ok(new ApiResponse("Success", interview));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/complete-interview/{id}")
    public ResponseEntity<?> completeInterview(@PathVariable Long id){
        try{
            Match match = interviewService.markComplete(id).getSlot().getMatch();
            emailService.sendFeedbackReminder(match);
            return ResponseEntity.ok(new ApiResponse("Success", null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }

    }
}