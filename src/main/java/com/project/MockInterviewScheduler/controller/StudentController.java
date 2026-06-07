package com.project.MockInterviewScheduler.controller;

import com.project.MockInterviewScheduler.dtos.*;
import com.project.MockInterviewScheduler.entity.*;
import com.project.MockInterviewScheduler.service.interfaces.StudentFeedbackServiceInterface;
import com.project.MockInterviewScheduler.service.interfaces.StudentServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/students")
public class StudentController {

    private final StudentServiceInterface studentService;
    private final StudentFeedbackServiceInterface feedbackService;
    private final InterviewSessionService interviewSessionService;

    @GetMapping("{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id){
        try{
            Student student = studentService.getStudentById(id);
            return ResponseEntity.ok().body(new ApiResponse("Success!",student));
        }catch (Exception e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("addStudent")
    public ResponseEntity<?> addStudent(@RequestBody Student student){
        try{
            Student newStudent = studentService.addStudent(student);
            return ResponseEntity.ok().body(new ApiResponse("Success!",newStudent));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("updateStudent/{id}")
    public ResponseEntity<?> updateStudent(@RequestBody Student student, Long id){
        try{
            Student updatedStudent = studentService.updateStudent(student,id);
            return ResponseEntity.ok().body(new ApiResponse("Success!",updatedStudent));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllStudents(){
        try{
            List<Student> students = studentService.getAllStudents();
            return ResponseEntity.ok().body(new ApiResponse("Success!",students));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id){
        try{
            studentService.deleteStudent(id);
            return ResponseEntity.ok().body(new ApiResponse("Success!",null));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("/{id}/expertise")
    public ResponseEntity<?> addExpertise(@RequestBody Expertise expertise,@PathVariable Long id){
        try{
            Expertise newExpertise = studentService.addExpertise(expertise,id);
            return ResponseEntity.ok().body(new ApiResponse("Success!",newExpertise));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("{id}/expertises")
    public ResponseEntity<?> getAllExpertiseById(@PathVariable Long id){
        try{
        List<Expertise> expertiseList = studentService.getAllExpertiseById(id);
        return ResponseEntity.ok().body(new ApiResponse("Success!",expertiseList));
        }catch (Exception e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("{id}/addSlot")
    public ResponseEntity<?> addSlot(@PathVariable Long id, @RequestBody AvailabilitySlot slot){
        try{
        AvailabilitySlot newSlot = studentService.addSlot(slot,id);
        return ResponseEntity.ok().body(new ApiResponse("Success!",newSlot));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("{id}/slots")
    public ResponseEntity<?> getAllSlotsById(Long id){
        try{
            List<AvailabilitySlot> slots = studentService.getAllAvailabilitySlotsById(id);
            return ResponseEntity.ok().body(new ApiResponse("Success!",slots));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("{userId}/updateSlots/{slotId}")
    public ResponseEntity<?> updateSlot(@PathVariable Long userId, @PathVariable Long slotId,@RequestBody AvailabilitySlot slot){
        try{
        AvailabilitySlot updatedSlot = studentService.updateSlot(slot,slotId,userId);
            return ResponseEntity.ok().body(new ApiResponse("Success!",updatedSlot));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("/accept/{id}")
    public ResponseEntity<?> acceptInterview(@PathVariable Long id){
        try{
            boolean isAccepted = studentService.acceptInterview(id);
            return ResponseEntity.ok().body(new ApiResponse("Success!",isAccepted));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("{userId}/feedback/{sessionId}")
    public ResponseEntity<?> addFeedback(@PathVariable Long userId, @PathVariable Long sessionId, @RequestBody StudentFeedback feedback){
        try{
            StudentFeedback newFeedback = studentService.addFeedback(feedback,sessionId,userId);
            return ResponseEntity.ok().body(new ApiResponse("Success!",newFeedback));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/feedback/{id}")
    public ResponseEntity<?> getFeedbackForStudent(@PathVariable Long id){
        try{
            InterviewerFeedback feedback = studentService.getFeedbackForStudent(id);
            return ResponseEntity.ok().body(new ApiResponse("Success!",feedback));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }


//    @GetMapping("/profile")
//    public ResponseEntity<?> getStudentProfile(@AuthenticationPrincipal CustomUser user){
//        try {
//            Long studentId = studentService.getStudentIdFromUser(user);
//            StudentProfileResponse response = studentService.getStudentProfile(studentId);
//            return ResponseEntity.ok().body(new ApiResponse("Success!",response));
//        }catch (UsernameNotFoundException e){
//            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
//        }
//    }
//
//    @PutMapping("/profile")
//    public ResponseEntity<?> updateStudentProfile(@AuthenticationPrincipal CustomUser user, StudentProfileRequest request){
//        try {
//            Long studentId = studentService.getStudentIdFromUser(user);
//            StudentProfileResponse response = studentService.updateProfile(request,studentId);
//            return ResponseEntity.ok().body(new ApiResponse("Success!",response));
//        }catch (UsernameNotFoundException e){
//            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
//        }
//    }
//
//    @PostMapping("/requests")
//    public ResponseEntity<?> addInterviewRequest(@AuthenticationPrincipal CustomUser user, CreateInterviewRequest request){
//        try {
//            Long studentId = studentService.getStudentIdFromUser(user);
//            InterviewRequestResponse response = studentService.createInterviewRequest(studentId,request);
//            return ResponseEntity.ok().body(new ApiResponse("Success!",response));
//        }catch (AlreadyExistsException e){
//            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(),null));
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
//        }
//    }
//
//    @GetMapping("/requests")
//    public ResponseEntity<?> getInterviewRequest(@AuthenticationPrincipal CustomUser user){
//        try {
//            Long studentId = studentService.getStudentIdFromUser(user);
//            InterviewRequestResponse response = studentService.getInterviewRequest(studentId);
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
//            Long studentId = studentService.getStudentIdFromUser(user);
//            SlotResponse response = studentService.addAvailabilitySlot(studentId,request);
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
//            Long studentId = studentService.getStudentIdFromUser(user);
//            SlotResponse response = studentService.getAvailabiltySlots(studentId);
//            return ResponseEntity.ok().body(new ApiResponse("Success!",response));
//        }catch (UsernameNotFoundException e){
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
//    public ResponseEntity<?> addFeedback(@PathVariable Long sessionId, @RequestBody FeedbackRequest request){
//        try {
//            FeedbackResponse response = feedbackService.createFeedbackForStudent(request,sessionId);
//            return ResponseEntity.ok().body(new ApiResponse("Success!",response));
//        }catch (UsernameNotFoundException e){
//            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
//        }
//    }
//
//    @GetMapping("/feedback/{sessionId}")
//    public ResponseEntity<?> getFeedback( @PathVariable Long sessionId){
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
//    public ResponseEntity<?> acceptInterview(@AuthenticationPrincipal CustomUser user,@PathVariable Long sessionId){
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
