package com.project.MockInterviewScheduler.service.interfaces;

import com.project.MockInterviewScheduler.dtos.requests.CreateUserRequest;
import com.project.MockInterviewScheduler.dtos.requests.LoginUserRequest;
import com.project.MockInterviewScheduler.dtos.responses.UserResponse;

public interface AuthServiceInterface {

    UserResponse login(LoginUserRequest request);
    UserResponse register(CreateUserRequest request);
}
