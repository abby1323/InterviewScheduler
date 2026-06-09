package com.project.MockInterviewScheduler.dtos.requests;

import lombok.Data;

import java.util.List;
@Data
public class ExpertiseRequest {
    private String domain;
    private List<String> subDomains;
}
