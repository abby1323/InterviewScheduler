package com.project.MockInterviewScheduler.service;

import com.project.MockInterviewScheduler.entity.AvailabilitySlot;
import com.project.MockInterviewScheduler.entity.CustomUser;
import com.project.MockInterviewScheduler.enums.SlotStatus;
import com.project.MockInterviewScheduler.exceptions.ResourceNotFoundException;
import com.project.MockInterviewScheduler.repository.SlotRepository;
import com.project.MockInterviewScheduler.service.interfaces.SlotServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SlotService implements SlotServiceInterface {

    private final SlotRepository slotRepository;
    @Override
    public AvailabilitySlot addSlot(AvailabilitySlot slot, CustomUser user) {
        AvailabilitySlot slot1 = new AvailabilitySlot();
        slot1.setStartTime(slot.getStartTime());
        slot1.setEndTime(slot.getEndTime());
        slot1.setUser(user);
        slot1.setStatus(SlotStatus.OPEN);
        user.getSlot().add(slot1);
        return slotRepository.save(slot1);
    }

    @Override
    public AvailabilitySlot updateSlot(AvailabilitySlot slot, Long slotId, CustomUser user) {
        AvailabilitySlot slot1 = getSlotById(slotId);
        slot1.setStartTime(slot.getStartTime());
        slot1.setEndTime(slot.getEndTime());
        return slotRepository.save(slot1);
    }


    @Override
    public AvailabilitySlot getSlotById(Long slotId){
        return slotRepository.findById(slotId).orElseThrow(() -> new ResourceNotFoundException("No such slot exists"));
    }
}
