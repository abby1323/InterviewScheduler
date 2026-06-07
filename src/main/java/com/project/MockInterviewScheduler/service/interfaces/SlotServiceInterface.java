package com.project.MockInterviewScheduler.service.interfaces;

import com.project.MockInterviewScheduler.entity.AvailabilitySlot;
import com.project.MockInterviewScheduler.entity.CustomUser;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SlotServiceInterface {

    AvailabilitySlot addSlot(AvailabilitySlot slot, CustomUser userId);
    AvailabilitySlot updateSlot(AvailabilitySlot slot, Long slotId, CustomUser user);

    AvailabilitySlot getSlotById(Long slotId);
}
