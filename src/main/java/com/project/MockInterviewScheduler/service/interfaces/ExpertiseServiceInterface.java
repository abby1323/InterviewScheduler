package com.project.MockInterviewScheduler.service.interfaces;

import com.project.MockInterviewScheduler.entity.CustomUser;
import com.project.MockInterviewScheduler.entity.Expertise;

import java.util.List;

public interface ExpertiseServiceInterface {

    Expertise addExpertise(Expertise expertise, CustomUser user);

}
