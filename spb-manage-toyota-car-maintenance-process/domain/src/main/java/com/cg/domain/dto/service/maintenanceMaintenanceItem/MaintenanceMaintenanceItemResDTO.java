package com.cg.domain.dto.service.maintenanceMaintenanceItem;

import com.cg.domain.dto.service.maintaince.MaintenanceResDTO;
import com.cg.domain.dto.service.maintenanceItem.MaintenanceItemResDTO;
import com.cg.domain.entity.service.ServiceArea;
import com.cg.domain.entity.service.maintenance.Maintenance;
import com.cg.domain.entity.service.maintenance.MaintenanceItem;
import com.cg.domain.enums.EMaintenanceChecklist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MaintenanceMaintenanceItemResDTO {
    private Long id;
    private String status;
    private MaintenanceResDTO maintenance;
    private MaintenanceItemResDTO maintenanceItem;
    public MaintenanceMaintenanceItemResDTO (Long id, EMaintenanceChecklist status, Maintenance maintenance, MaintenanceItem maintenanceItem) {
        this.id = id;
        this.status = status.getValue();
        this.maintenance = maintenance.toMaintenanceResDTO();
        this.maintenanceItem = maintenanceItem.toMaintenanceItemResDTO();
    }
}
