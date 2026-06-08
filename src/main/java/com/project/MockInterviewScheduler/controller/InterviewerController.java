package com.project.MockInterviewScheduler.controller;

import com.project.MockInterviewScheduler.dtos.requests.ExpertiseRequest;
import com.project.MockInterviewScheduler.dtos.requests.InterviewerFeedbackRequest;
import com.project.MockInterviewScheduler.dtos.requests.InterviewerRequest;
import com.project.MockInterviewScheduler.dtos.requests.SlotRequest;
import com.project.MockInterviewScheduler.dtos.responses.*;
import com.project.MockInterviewScheduler.entity.*;
import com.project.MockInterviewScheduler.service.interfaces.InterviewerServiceInterface;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/interviewers")
public class InterviewerController {

    private final InterviewerServiceInterface interviewerService;
    private final ModelMapper mapper;

    // handled by AuthController
//    @PostMapping("/")
//    public ResponseEntity<?> addInterviewer(@RequestBody InterviewerRequest interviewerRequest) {
//        Interviewer newInterviewer = interviewerService.addInterviewer(getInterviewerEntity(interviewerRequest));
//        InterviewResponse response = getInterviewerResponse(newInterviewer);
//        return ResponseEntity.ok().body(new ApiResponse("Success!", response));
//    }

    private Interviewer getInterviewerEntity(InterviewerRequest interviewerRequest) {
        return mapper.map(interviewerRequest, Interviewer.class);
    }

    private InterviewResponse getInterviewerResponse(Interviewer newInterviewer) {
        return mapper.map(newInterviewer, InterviewResponse.class);
    }

    @GetMapping("/interviewer")
    public ResponseEntity<?> getInterviewerById(@AuthenticationPrincipal CustomUser user) {
        Interviewer interviewer = interviewerService.getInterviewerById(user.getId());
        InterviewResponse response = getInterviewerResponse(interviewer);
        return ResponseEntity.ok().body(new ApiResponse("Success!", response));
    }

    @PutMapping("/update/")
    public ResponseEntity<?> updateInterviewer(@AuthenticationPrincipal CustomUser user, @RequestBody InterviewerRequest request) {
        Interviewer updatedInterviewer = interviewerService.updateInterviewer(getInterviewerEntity(request), user.getId());
        InterviewResponse response = getInterviewerResponse(updatedInterviewer);
        return ResponseEntity.ok().body(new ApiResponse("Success!", response));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteInterviewer(@AuthenticationPrincipal CustomUser user) {
        interviewerService.deleteInterviewer(user.getId());
        return ResponseEntity.ok().body(new ApiResponse("Success!", null));
    }

    @PostMapping("/expertise")
    public ResponseEntity<?> addExpertise(@RequestBody ExpertiseRequest request, @AuthenticationPrincipal CustomUser user) {
        Expertise newExpertise = interviewerService.addExpertise(getExpertiseEntity(request), user.getId());
        ExpertiseResponse response = getExpertiseResponse(newExpertise);
        return ResponseEntity.ok().body(new ApiResponse("Success!", response));
    }

    private Expertise getExpertiseEntity(ExpertiseRequest request) {
        return mapper.map(request, Expertise.class);
    }

    private ExpertiseResponse getExpertiseResponse(Expertise newExpertise) {
        return mapper.map(newExpertise, ExpertiseResponse.class);
    }

    @GetMapping("expertises/all")
    public ResponseEntity<?> getAllExpertiseById(@AuthenticationPrincipal CustomUser user) {
        List<Expertise> expertiseList = interviewerService.getAllExpertiseByUserId(user.getId());
        List<ExpertiseResponse> responses = expertiseList.stream()
                .map(this::getExpertiseResponse).toList();
        return ResponseEntity.ok().body(new ApiResponse("Success!", responses));
    }

    @PostMapping("/addSlot")
    public ResponseEntity<?> addSlot(@AuthenticationPrincipal CustomUser user, @RequestBody SlotRequest request) {
        AvailabilitySlot newSlot = interviewerService.addSlot(getSlotEntity(request), user.getId());
        SlotResponse response = getSlotResponse(newSlot);
        return ResponseEntity.ok().body(new ApiResponse("Success!", response));
    }

    private AvailabilitySlot getSlotEntity(SlotRequest request) {
        return mapper.map(request, AvailabilitySlot.class);
    }

    private SlotResponse getSlotResponse(AvailabilitySlot newSlot) {
        return mapper.map(newSlot, SlotResponse.class);
    }

    @GetMapping("/slots")
    public ResponseEntity<?> getAllSlotsById(@AuthenticationPrincipal CustomUser user) {
        List<AvailabilitySlot> slots = interviewerService.getAllAvailabilitySlotsByUserId(user.getId());
        List<SlotResponse> slotResponses = slots.stream()
                .map(this::getSlotResponse).toList();
        return ResponseEntity.ok().body(new ApiResponse("Success!", slotResponses));
    }

    @PutMapping("updateSlot/{slotId}")
    public ResponseEntity<?> updateSlot(@AuthenticationPrincipal CustomUser user, @PathVariable Long slotId, @RequestBody SlotRequest request) {
        AvailabilitySlot updatedSlot = interviewerService.updateSLot(getSlotEntity(request), slotId, user.getId());
        SlotResponse response = getSlotResponse(updatedSlot);
        return ResponseEntity.ok().body(new ApiResponse("Success!", response));
    }

    @PostMapping("/accept/{id}")
    public ResponseEntity<?> acceptInterview(@PathVariable Long id, @RequestParam boolean isAccepted, @AuthenticationPrincipal CustomUser user) {
        boolean isAcceptedI = interviewerService.acceptInterview(user,id,isAccepted);
        return ResponseEntity.ok().body(new ApiResponse("Success!", isAcceptedI));
    }

    @PostMapping("feedback/{sessionId}")
    public ResponseEntity<?> addFeedback(@AuthenticationPrincipal CustomUser user, @PathVariable Long sessionId, @RequestBody InterviewerFeedbackRequest request) {
        InterviewerFeedback newFeedback = interviewerService.addFeedback(getIFeedbackEntity(request), sessionId, user.getId());
        InterviewerFeedbackResponse response = getInterviewerFeedbackResponse(newFeedback);
        return ResponseEntity.ok().body(new ApiResponse("Success!", response));
    }

    private InterviewerFeedback getIFeedbackEntity(InterviewerFeedbackRequest request) {
        return mapper.map(request, InterviewerFeedback.class);
    }

    @GetMapping("/feedback/{interviewId}")
    public ResponseEntity<?> getFeedbackForInterviewer(@PathVariable Long interviewId,@AuthenticationPrincipal CustomUser user) {
        StudentFeedback feedback = interviewerService.getFeedbackForInterviewer(interviewId,user.getId());
        StudentFeedbackResponse response = getStudentFeedbackResponse(feedback);
        return ResponseEntity.ok().body(new ApiResponse("Success!", response));
    }

    private StudentFeedbackResponse getStudentFeedbackResponse(StudentFeedback newFeedback) {
        return mapper.map(newFeedback, StudentFeedbackResponse.class);
    }

    private InterviewerFeedbackResponse getInterviewerFeedbackResponse(InterviewerFeedback feedback) {
        return mapper.map(feedback, InterviewerFeedbackResponse.class);
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
//        }catch (ResourceAlreadyException e){
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
//        }catch (ResourceAlreadyException e){
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
