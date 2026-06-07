package com.project.MockInterviewScheduler.controller;

import com.project.MockInterviewScheduler.dtos.*;
import com.project.MockInterviewScheduler.entity.*;
import com.project.MockInterviewScheduler.service.interfaces.InterviewerServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/interviewers")
public class InterviewerController {

    private final InterviewerServiceInterface interviewerService;

    @PostMapping("/")
    public ResponseEntity<?> addInterviewer(@RequestBody Interviewer interviewer){
        try{
        Interviewer newInterviewer = interviewerService.addInterviewer(interviewer);
            return ResponseEntity.ok().body(new ApiResponse("Success!",newInterviewer));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInterviewerById(@PathVariable Long id){
        try{
            Interviewer interviewer = interviewerService.getInterviewerById(id);
            return ResponseEntity.ok().body(new ApiResponse("Success!",interviewer));
        }catch (Exception e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/{id}/")
    public ResponseEntity<?> updateInterviewer(@PathVariable Long id, @RequestBody Interviewer interviewer){
        try{
            Interviewer updatedInterviewer = interviewerService.updateInterviewer(interviewer,id);
            return ResponseEntity.ok().body(new ApiResponse("Success!",updatedInterviewer));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInterviewer(@PathVariable Long id){
        try{
            interviewerService.deleteInterviewer(id);
            return ResponseEntity.ok().body(new ApiResponse("Success!",null));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("/{id}/expertise")
    public ResponseEntity<?> addExpertise(@RequestBody Expertise expertise,@PathVariable Long id){
        try{
            Expertise newExpertise = interviewerService.addExpertise(expertise,id);
            return ResponseEntity.ok().body(new ApiResponse("Success!",newExpertise));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("{id}/expertises")
    public ResponseEntity<?> getAllExpertiseById(@PathVariable Long id){
        try{
            List<Expertise> expertiseList = interviewerService.getAllExpertiseByUserId(id);
            return ResponseEntity.ok().body(new ApiResponse("Success!",expertiseList));
        }catch (Exception e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("{id}/addSlot")
    public ResponseEntity<?> addSlot(@PathVariable Long id, @RequestBody AvailabilitySlot slot){
        try{
            AvailabilitySlot newSlot = interviewerService.addSlot(slot,id);
            return ResponseEntity.ok().body(new ApiResponse("Success!",newSlot));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("{id}/slots")
    public ResponseEntity<?> getAllSlotsById(@PathVariable Long id){
        try{
            List<AvailabilitySlot> slots = interviewerService.getAllAvailabilitySlotsByUserId(id);
            return ResponseEntity.ok().body(new ApiResponse("Success!",slots));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("{userId}/updateSlots/{slotId}")
    public ResponseEntity<?> updateSlot(@PathVariable Long userId, @PathVariable Long slotId,@RequestBody AvailabilitySlot slot){
        try{
            AvailabilitySlot updatedSlot = interviewerService.updateSLot(slot,slotId,userId);
            return ResponseEntity.ok().body(new ApiResponse("Success!",updatedSlot));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("/accept/{id}")
    public ResponseEntity<?> acceptInterview(@PathVariable Long id, @RequestParam boolean isAccepted){
        try{
            boolean isAcceptedI = interviewerService.acceptInterview(id,isAccepted);
            return ResponseEntity.ok().body(new ApiResponse("Success!",isAcceptedI));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("{userId}/feedback/{sessionId}")
    public ResponseEntity<?> addFeedback(@PathVariable Long userId, @PathVariable Long sessionId, @RequestBody InterviewerFeedback feedback){
        try{
            InterviewerFeedback newFeedback = interviewerService.addFeedback(feedback,sessionId,userId);
            return ResponseEntity.ok().body(new ApiResponse("Success!",newFeedback));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/feedback/{id}")
    public ResponseEntity<?> getFeedbackForInterviewer(@PathVariable Long id){
        try{
            StudentFeedback feedback = interviewerService.getFeedbackForInterviewer(id);
            return ResponseEntity.ok().body(new ApiResponse("Success!",feedback));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }





//    private final InterviewerService interviewerService;
//    private final InterviewSessionService interviewSessionService;
//    private final FeedbackService feedbackService;
//
//    @GetMapping("/profile")
//    public ResponseEntity<?> getInterviewerProfile(@AuthenticationPrincipal CustomUser user){
//        try {
//            Long interviewerId = interviewerService.getInterviewerIdFromUser(user);
//            InterviewerProfileResponse response = interviewerService.getInterviewerProfile(interviewerId);
//            return ResponseEntity.ok().body(new ApiResponse("Success!",response));
//        }catch (UsernameNotFoundException e){
//            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
//        }
//    }
//
//    @PutMapping("/profile")
//    public ResponseEntity<?> updateInterviewerProfile(@AuthenticationPrincipal CustomUser user, InterviewerProfileRequest request){
//        try {
//            Long interviewerId = interviewerService.getInterviewerIdFromUser(user);
//            InterviewerProfileResponse response = interviewerService.updateProfile(request,interviewerId);
//            return ResponseEntity.ok().body(new ApiResponse("Success!",response));
//        }catch (UsernameNotFoundException e){
//            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
//        }
//    }
//
//    @PostMapping("/slots")
//    public ResponseEntity<?> addAvailSlots(@AuthenticationPrincipal CustomUser user,SlotRequest request){
//        try {
//            Long interviewerId = interviewerService.getInterviewerIdFromUser(user);
//            SlotResponse response = interviewerService.addAvailabilitySlot(interviewerId,request);
//            return ResponseEntity.ok().body(new ApiResponse("Success!",response));
//        }catch (AlreadyExistsException e){
//            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(),null));
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
//        }
//    }
//
//    @GetMapping("/slots")
//    public ResponseEntity<?> getAvailSlots(@AuthenticationPrincipal CustomUser user){
//        try {
//            Long interviewerId = interviewerService.getInterviewerIdFromUser(user);
//            SlotResponse response = interviewerService.getAvailabilitySlots(interviewerId);
//            return ResponseEntity.ok().body(new ApiResponse("Success!",response));
//        }catch (AlreadyExistsException e){
//            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
//        }
//    }
//
//    @PutMapping("/interviews/cancel/{sessionId}")
//    public ResponseEntity<?> cancelInterview(@AuthenticationPrincipal CustomUser user, @PathVariable Long sessionId){
//        try {
//            interviewSessionService.cancelInterview(sessionId);
//            return ResponseEntity.ok().body(new ApiResponse("Success!",null));
//        }catch (UsernameNotFoundException e){
//            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
//        }
//    }
//
//    @PostMapping("/feedback/{sessionId}")
//    public ResponseEntity<?> addFeedback(@AuthenticationPrincipal CustomUser user, @PathVariable Long sessionId, @RequestBody FeedbackRequest request){
//        try {
//            FeedbackResponse response = feedbackService.createFeedbackForInterviewer(request,sessionId);
//            return ResponseEntity.ok().body(new ApiResponse("Success!",response));
//        }catch (UsernameNotFoundException e){
//            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
//        }
//    }
//
//    @GetMapping("/feedback/{sessionId}")
//    public ResponseEntity<?> getFeedback(@AuthenticationPrincipal CustomUser user, @PathVariable Long sessionId){
//        try {
//            List<FeedbackResponse> responses = feedbackService.getFeedback(sessionId);
//            return ResponseEntity.ok().body(new ApiResponse("Success!",responses));
//        }catch (UsernameNotFoundException e){
//            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
//        }
//    }
//
//    @PutMapping("/interviews/accept/{sessionId}")
//    public ResponseEntity<?> acceptInterview(@PathVariable Long sessionId){
//        try {
//            interviewSessionService.acceptInterview(sessionId);
//            return ResponseEntity.ok().body(new ApiResponse("Success!",null));
//        }catch (UsernameNotFoundException e){
//            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
//        }
//    }
}
