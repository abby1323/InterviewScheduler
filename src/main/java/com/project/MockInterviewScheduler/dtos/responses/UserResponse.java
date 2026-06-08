package com.project.MockInterviewScheduler.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String token;
    private String email;

    public UserResponse(String email){
        this.email=email;
    }
}
