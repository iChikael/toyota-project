package com.cg.domain.dto.service.maintenanceItemAccessory;


import com.cg.domain.dto.accessory.AccessoryReceptionResDTO;
import com.cg.domain.dto.service.maintenanceItem.MaintenanceItemResDTO;
import com.cg.domain.entity.service.maintenance.MaintenanceItem;
import com.cg.domain.entity.storage.Accessory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MaintenanceItemAccessoryResDTO {
    private Long id;
    private AccessoryReceptionResDTO accessory;
    private MaintenanceItemResDTO maintenanceItem;

    public MaintenanceItemAccessoryResDTO (Long id, Accessory accessory, MaintenanceItem maintenanceItem) {
        this.id = id;
        this.accessory = accessory.toAccessoryReceptionResDTO();
        this.maintenanceItem = maintenanceItem.toMaintenanceItemResDTO();

    }
}
