package com.project.MockInterviewScheduler.service;

import com.project.MockInterviewScheduler.entity.InterviewRequest;
import com.project.MockInterviewScheduler.enums.InterviewRequestStatus;
import com.project.MockInterviewScheduler.repository.InterviewRequestRepository;
import com.project.MockInterviewScheduler.service.interfaces.InterviewRequestServiceInterface;
import com.project.MockInterviewScheduler.service.interfaces.StudentServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InterviewRequestService implements InterviewRequestServiceInterface {

    private final InterviewRequestRepository interviewRequestRepository;
    private final StudentServiceInterface studentService;
    @Override
    public InterviewRequest addRequest(Long id) {
        InterviewRequest request = new InterviewRequest();
        request.setStudent(studentService.getStudentById(id));
        request.setStatus(InterviewRequestStatus.PENDING);
        return interviewRequestRepository.save(request);
    }

    @Override
    public InterviewRequest deleteInterviewRequest(Long id) {
        InterviewRequest request = getInterviewRequestByUserId(id);
        request.setStatus(InterviewRequestStatus.CANCELLED);
        return null;
    }

    @Override
    public InterviewRequest getInterviewRequestByUserId(Long userId) {
        return interviewRequestRepository.findByStudentId(userId).orElseThrow(() -> new RuntimeException("No such user exists"));
    }

    @Override
    public List<InterviewRequest> getAllPendingRequests() {
        return interviewRequestRepository.findAll()
                .stream()
                .filter(request -> InterviewRequestStatus.PENDING.equals(request.getStatus()))
                .toList();
    }

    @Override
    public InterviewRequest getRequestById(Long id) {
        return interviewRequestRepository.findById(id).orElseThrow(() -> new RuntimeException("No such request exists"));
    }


//    public InterviewRequest addInterviewRequest(CreateInterviewRequest createRequest, Student student){
//        InterviewRequest request = new InterviewRequest();
//        request.setStudent(student);
//        request.setDifficultyLevel(createRequest.getDifficultyLevel());
//        request.setStatus(InterviewRequestStatus.PENDING);
//        return interviewRequestRepository.save(request);
//    }
//
//    public InterviewRequest getInterviewRequest(Long studentId){
//        return interviewRequestRepository.findByStudentId(studentId)
//                .orElseThrow(() -> new UsernameNotFoundException("Not found"));
//    }
//
//    public void updateMatchToInterviewRequest(InterviewRequest request){
//        request.setStatus(InterviewRequestStatus.MATCHED);
//        interviewRequestRepository.save(request);
//    }
//
//    public InterviewRequest getInterviewRequestById(Long requestId) {
//        return interviewRequestRepository.findById(requestId)
//                .orElseThrow();
//    }
}
