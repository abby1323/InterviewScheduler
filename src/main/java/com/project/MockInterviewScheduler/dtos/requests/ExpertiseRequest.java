package com.project.MockInterviewScheduler.dtos.requests;

import lombok.Data;

import java.util.List;
@Data
public class ExpertiseRequest {
    private String domains;
    private List<String> subDomains;
}
