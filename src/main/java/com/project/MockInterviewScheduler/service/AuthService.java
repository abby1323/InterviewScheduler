package com.project.MockInterviewScheduler.service;

import com.project.MockInterviewScheduler.dtos.requests.CreateUserRequest;
import com.project.MockInterviewScheduler.dtos.requests.LoginUserRequest;
import com.project.MockInterviewScheduler.dtos.responses.UserResponse;
import com.project.MockInterviewScheduler.entity.CustomUser;
import com.project.MockInterviewScheduler.entity.Interviewer;
import com.project.MockInterviewScheduler.entity.PlacementCoordinator;
import com.project.MockInterviewScheduler.entity.Student;
import com.project.MockInterviewScheduler.enums.InterviewerStatus;
import com.project.MockInterviewScheduler.exceptions.InvalidRequestException;
import com.project.MockInterviewScheduler.repository.CustomUserRepository;
import com.project.MockInterviewScheduler.repository.InterviewerRepository;
import com.project.MockInterviewScheduler.repository.PlacementCoordRepository;
import com.project.MockInterviewScheduler.repository.StudentRepository;
import com.project.MockInterviewScheduler.security.JwtService;
import com.project.MockInterviewScheduler.service.interfaces.AuthServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthServiceInterface {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    private final CustomUserRepository userRepository;
    private final StudentRepository studentRepository;
    private final InterviewerRepository interviewerRepository;
    private final PlacementCoordRepository placementCoordRepository;

    @Override
    public UserResponse login(LoginUserRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        CustomUser user = userRepository.findByEmail(request.getEmail()).orElseThrow(()-> new UsernameNotFoundException("No such user exists"));
        String token = jwtService.generateToken(user);
        return new UserResponse(token,user.getEmail());
    }

    @Override
    public UserResponse register(CreateUserRequest request) {
        CustomUser savedUser;


        switch (request.getRole()){
            case STUDENT -> {
                Student student = new Student();
                student.setEmail(request.getEmail());
                student.setPassword(passwordEncoder.encode(request.getPassword()));
                student.setRole(request.getRole());
                student.setActive(true);
                student.setName(request.getName());
                student.setCollege(request.getCollege());
                student.setBranch(request.getBranch());
                student.setTargetRole(request.getTargetRole());

                savedUser = studentRepository.save(student);
            }
            case INTERVIEWER -> {
                Interviewer interviewer = new Interviewer();
                interviewer.setEmail(request.getEmail());
                interviewer.setPassword(passwordEncoder.encode(request.getPassword()));
                interviewer.setRole(request.getRole());
                interviewer.setActive(true);
                interviewer.setName(request.getName());
                interviewer.setCompany(request.getCompany());
                interviewer.setExperienceInYears(request.getExperienceInYears());
                interviewer.setBroadExpertiseBranch(request.getBroadExpertiseBranch());
                interviewer.setStatus(InterviewerStatus.PENDING);

                savedUser = interviewerRepository.save(interviewer);
            }
            case PLACEMENT_ADMIN -> {
                PlacementCoordinator admin = new PlacementCoordinator();
                admin.setEmail(request.getEmail());
                admin.setRole(request.getRole());
                admin.setPassword(passwordEncoder.encode(request.getPassword()));
                admin.setName(request.getName());
                admin.setActive(true);
                savedUser = placementCoordRepository.save(admin);
            }
            default -> throw new InvalidRequestException("Invalid role");
        }

        String token = jwtService.generateToken(savedUser);
        return new UserResponse(token, savedUser.getEmail());
    }
}
