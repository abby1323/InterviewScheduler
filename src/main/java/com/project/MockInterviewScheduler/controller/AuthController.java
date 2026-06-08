package com.project.MockInterviewScheduler.controller;

import com.project.MockInterviewScheduler.dtos.requests.CreateUserRequest;
import com.project.MockInterviewScheduler.dtos.requests.LoginUserRequest;
import com.project.MockInterviewScheduler.dtos.responses.ApiResponse;
import com.project.MockInterviewScheduler.dtos.responses.UserResponse;
import com.project.MockInterviewScheduler.service.interfaces.AuthServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthServiceInterface authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserRequest request){
        UserResponse response = authService.login(request);
        return ResponseEntity.ok().body(new ApiResponse("Success",response));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody CreateUserRequest request){
        UserResponse response = authService.register(request);
        return ResponseEntity.ok().body(new ApiResponse("Success",response));
    }


}
