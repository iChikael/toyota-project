package com.cg.domain.dto.service.orderService.orderMaintenanceItem;

import com.cg.domain.dto.service.maintenanceItem.MaintenanceItemResDTO;
import com.cg.domain.dto.service.orderService.orderMaintenanceItemAccessory.OrderMaintenanceItemAccessoryResDTO;
import com.cg.domain.entity.service.maintenance.MaintenanceItem;
import com.cg.domain.enums.EMaintenanceChecklist;
import com.cg.domain.enums.EStatusOrderServiceDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderMaintenanceItemResDTO {

    private Long id;
    private String serviceItemName;
    private String checklistName;
    private String status;
    private MaintenanceItemResDTO maintenanceItem;
    private List<OrderMaintenanceItemAccessoryResDTO> orderMaintenanceItemAccessories;

    public OrderMaintenanceItemResDTO (Long id, String serviceItemName, EMaintenanceChecklist checklistName, EStatusOrderServiceDetail status, MaintenanceItem maintenanceItem) {
        this.id = id;
        this.serviceItemName = serviceItemName;
        this.checklistName = checklistName.getValue();
        this.status = status.getValue();
        this.maintenanceItem = maintenanceItem.toMaintenanceItemResDTO();
    }
}
