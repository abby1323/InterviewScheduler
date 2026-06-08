package com.project.MockInterviewScheduler.controller;

import com.project.MockInterviewScheduler.dtos.requests.ExpertiseRequest;
import com.project.MockInterviewScheduler.dtos.requests.SlotRequest;
import com.project.MockInterviewScheduler.dtos.requests.StudentFeedbackRequest;
import com.project.MockInterviewScheduler.dtos.requests.StudentRequest;
import com.project.MockInterviewScheduler.dtos.responses.*;
import com.project.MockInterviewScheduler.entity.*;
import com.project.MockInterviewScheduler.service.interfaces.StudentServiceInterface;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/students")
public class StudentController {

    private final StudentServiceInterface studentService;
    private final ModelMapper mapper;


    @GetMapping("/me")
    public ResponseEntity<?> getStudentById(@AuthenticationPrincipal CustomUser  user) {
        try {
            Student student = studentService.getStudentById(user.getId());
            StudentResponse response = getStudentResponse(student);
            return ResponseEntity.ok().body(new ApiResponse("Success!", response));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    private StudentResponse getStudentResponse(Student student) {
        return mapper.map(student, StudentResponse.class);
    }

    // handled by AuthController
//    @PostMapping("addStudent")
//    public ResponseEntity<?> addStudent(@RequestBody StudentRequest student) {
//        try {
//            Student newStudent = studentService.addStudent(getStudentEntity(student));
//            StudentResponse response = getStudentResponse(newStudent);
//            return ResponseEntity.ok().body(new ApiResponse("Success!", response));
//        } catch (Exception e) {
//            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
//        }
//    }

    private Student getStudentEntity(StudentRequest student) {
        return mapper.map(student, Student.class);
    }

    @PutMapping("updateStudent/")
    public ResponseEntity<?> updateStudent(@RequestBody StudentRequest student, @AuthenticationPrincipal CustomUser  user) {
        try {
            Student updatedStudent = studentService.updateStudent(getStudentEntity(student), user.getId());
            StudentResponse response = getStudentResponse(updatedStudent);
            return ResponseEntity.ok().body(new ApiResponse("Success!", response));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllStudents() {
        try {
            List<Student> students = studentService.getAllStudents();
            List<StudentResponse> responses = students.stream()
                    .map(this::getStudentResponse).toList();
            return ResponseEntity.ok().body(new ApiResponse("Success!", responses));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/")
    public ResponseEntity<?> deleteStudent(@AuthenticationPrincipal CustomUser  user) {
        try {
            studentService.deleteStudent(user.getId());
            return ResponseEntity.ok().body(new ApiResponse("Success!", null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/expertise")
    public ResponseEntity<?> addExpertise(@RequestBody ExpertiseRequest expertise, @AuthenticationPrincipal CustomUser  user) {
        try {
            Expertise newExpertise = studentService.addExpertise(getExpertiseEntity(expertise), user.getId());
            ExpertiseResponse response = getExpertiseResponse(newExpertise);
            return ResponseEntity.ok().body(new ApiResponse("Success!", response));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    private ExpertiseResponse getExpertiseResponse(Expertise expertise) {
        return mapper.map(expertise, ExpertiseResponse.class);
    }

    @GetMapping("/expertises")
    public ResponseEntity<?> getAllExpertiseById(@AuthenticationPrincipal CustomUser  user) {
        try {
            List<Expertise> expertiseList = studentService.getAllExpertiseById(user.getId());
            List<ExpertiseResponse> responses = expertiseList.stream()
                    .map(this::getExpertiseResponse).toList();
            return ResponseEntity.ok().body(new ApiResponse("Success!", responses));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/addSlot")
    public ResponseEntity<?> addSlot(@AuthenticationPrincipal CustomUser  user, @RequestBody SlotRequest slot) {
        try {
            AvailabilitySlot newSlot = studentService.addSlot(getSlotEntity(slot), user.getId());
            SlotResponse response = getSlotResponse(newSlot);
            return ResponseEntity.ok().body(new ApiResponse("Success!", response));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    private SlotResponse getSlotResponse(AvailabilitySlot newSlot) {
        return mapper.map(newSlot, SlotResponse.class);
    }

    @GetMapping("/slots")
    public ResponseEntity<?> getAllSlotsById(@AuthenticationPrincipal CustomUser  user) {
        try {
            List<AvailabilitySlot> slots = studentService.getAllAvailabilitySlotsById(user.getId());
            List<SlotResponse> responses = slots.stream()
                    .map(this::getSlotResponse).toList();
            return ResponseEntity.ok().body(new ApiResponse("Success!", responses));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/updateSlots/{slotId}")
    public ResponseEntity<?> updateSlot(@AuthenticationPrincipal CustomUser  user, @PathVariable Long slotId, @RequestBody SlotRequest slot) {
        try {
            AvailabilitySlot updatedSlot = studentService.updateSlot(getSlotEntity(slot), slotId, user.getId());
            SlotResponse response = getSlotResponse(updatedSlot);
            return ResponseEntity.ok().body(new ApiResponse("Success!", response));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/interview-request")
    public ResponseEntity<?> addInterviewRequest(@AuthenticationPrincipal CustomUser  user) {
        InterviewRequest request = studentService.makeInterviewRequest(user.getId());
        return ResponseEntity.ok()
                .body(new ApiResponse("Success!", getInterviewRequestResponse(request)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteInterviewRequest(@PathVariable Long id, @AuthenticationPrincipal CustomUser  user) {
        studentService.deleteInterviewRequest(id,user.getId());
        return ResponseEntity.ok()
                .body(new ApiResponse("Success", null));
    }

    private InterviewRequestResponse getInterviewRequestResponse(InterviewRequest request) {
        return mapper.map(request, InterviewRequestResponse.class);
    }

    @PostMapping("/accept/{id}")
    public ResponseEntity<?> acceptInterview(@AuthenticationPrincipal CustomUser  user,@PathVariable Long id, @RequestParam boolean isAccepted) {
        try {
            boolean isAcceptedSure = studentService.acceptInterview(user,id, isAccepted);
            return ResponseEntity.ok().body(new ApiResponse("Success!", isAcceptedSure));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/feedback/{sessionId}")
    public ResponseEntity<?> addFeedback(@AuthenticationPrincipal CustomUser  user, @PathVariable Long sessionId, @RequestBody StudentFeedbackRequest feedback) {
        try {
            StudentFeedback newFeedback = studentService.addFeedback(getIFeedbackEntity(feedback), sessionId, user.getId());
            StudentFeedbackResponse response = getStudentFeedbackResponse(newFeedback);
            return ResponseEntity.ok().body(new ApiResponse("Success!", response));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    private StudentFeedback getIFeedbackEntity(StudentFeedbackRequest request) {
        return mapper.map(request, StudentFeedback.class);
    }

    private StudentFeedbackResponse getStudentFeedbackResponse(StudentFeedback newFeedback) {
        return mapper.map(newFeedback, StudentFeedbackResponse.class);
    }

    @GetMapping("/feedback/{id}")
    public ResponseEntity<?> getFeedbackForStudent(@PathVariable Long id, @AuthenticationPrincipal CustomUser user) {
        try {
            InterviewerFeedback feedback = studentService.getFeedbackForStudent(id,user.getId());
            InterviewerFeedbackResponse response = getInterviewerFeedbackResponse(feedback);
            return ResponseEntity.ok().body(new ApiResponse("Success!", response));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    private InterviewerFeedbackResponse getInterviewerFeedbackResponse(InterviewerFeedback feedback) {
        return mapper.map(feedback, InterviewerFeedbackResponse.class);
    }

    private AvailabilitySlot getSlotEntity(SlotRequest request) {
        return mapper.map(request, AvailabilitySlot.class);
    }

    private Expertise getExpertiseEntity(ExpertiseRequest request) {
        return mapper.map(request, Expertise.class);
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
//        }catch (ResourceAlreadyException e){
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
