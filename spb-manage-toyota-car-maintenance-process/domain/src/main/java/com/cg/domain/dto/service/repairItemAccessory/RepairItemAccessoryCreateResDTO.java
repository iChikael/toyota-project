package com.cg.domain.dto.service.repairItemAccessory;

import com.cg.domain.dto.accessory.AccessoryResDTO;
import com.cg.domain.dto.service.repairItem.RepairItemResDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RepairItemAccessoryCreateResDTO {

    private RepairItemResDTO repairItem;

    private AccessoryResDTO accessory;
}
