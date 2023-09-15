package com.cg.service.service.repairItemAccessory;

import com.cg.domain.dto.service.repairItemAccessory.RepairItemAccessoryResDTO;
import com.cg.domain.entity.service.repair.RepairItemAccessory;
import com.cg.service.IGeneralService;

import java.util.List;


public interface IRepairItemAccessoryService extends IGeneralService<RepairItemAccessory, Long> {
    List<RepairItemAccessoryResDTO> findAllByRepairItemId (Long repairItemId);
}
