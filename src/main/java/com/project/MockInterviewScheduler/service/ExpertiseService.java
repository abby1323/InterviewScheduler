package com.project.MockInterviewScheduler.service;

import com.project.MockInterviewScheduler.entity.CustomUser;
import com.project.MockInterviewScheduler.entity.Expertise;
import com.project.MockInterviewScheduler.repository.ExpertiseRepository;
import com.project.MockInterviewScheduler.service.interfaces.ExpertiseServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpertiseService implements ExpertiseServiceInterface {

    private final ExpertiseRepository expertiseRepository;

    @Override
    public Expertise addExpertise(Expertise expertise, CustomUser user){
        Expertise expertise1 = new Expertise();
        expertise1.setUser(user);
        expertise1.setDomain(expertise.getDomain());
        expertise1.setSubDomains(expertise.getSubDomains());
        user.getExpertise().add(expertise1);
        return expertiseRepository.save(expertise1);
    }


}
