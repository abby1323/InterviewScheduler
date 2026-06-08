package com.project.MockInterviewScheduler.dtos.responses;

import lombok.Data;

import java.util.List;

@Data
public class ExpertiseResponse {
    private Long id;
    private String domain;
    private List<String> subDomains;
}
