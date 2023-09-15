package com.cg.service.service.maintenance;

import com.cg.domain.dto.service.maintenanceMaintenanceItem.MaintenanceMaintenanceItemResDTO;
import com.cg.domain.entity.service.maintenance.MaintenanceMaintenanceItem;
import com.cg.service.IGeneralService;

import java.util.List;

public interface IMaintenanceMaintenanceItemService extends IGeneralService<MaintenanceMaintenanceItem, Long> {
    List<MaintenanceMaintenanceItemResDTO> findAllByMaintenance_Id (Long maintenanceId);
}
