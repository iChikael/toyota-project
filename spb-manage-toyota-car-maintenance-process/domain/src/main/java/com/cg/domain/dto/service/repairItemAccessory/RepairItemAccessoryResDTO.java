package com.cg.domain.dto.service.repairItemAccessory;

import com.cg.domain.dto.accessory.AccessoryReceptionResDTO;
import com.cg.domain.dto.service.repairItem.RepairItemResDTO;
import com.cg.domain.entity.service.repair.RepairItem;
import com.cg.domain.entity.storage.Accessory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RepairItemAccessoryResDTO {
    private Long id;
    private RepairItemResDTO repairItem;

    private AccessoryReceptionResDTO accessory;


    public RepairItemAccessoryResDTO (Long id, Accessory accessory, RepairItem repairItem) {
        this.id = id;
        this.accessory = accessory.toAccessoryReceptionResDTO();
        this.repairItem = repairItem.toRepairItemResDTO();

    }
}
