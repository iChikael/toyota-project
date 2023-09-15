package com.cg.repository.service.maintenance;

import com.cg.domain.dto.service.maintenanceMaintenanceItem.MaintenanceMaintenanceItemResDTO;
import com.cg.domain.entity.service.maintenance.Maintenance;
import com.cg.domain.entity.service.maintenance.MaintenanceMaintenanceItem;
import com.cg.domain.enums.EMaintenanceChecklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMaintenanceMaintenanceItemRepository extends JpaRepository <MaintenanceMaintenanceItem, Long> {
    List<MaintenanceMaintenanceItem> findAllByMaintenanceAndNameIsNotLike(Maintenance maintenance, EMaintenanceChecklist eMaintenanceChecklist);

    @Query ("SELECT NEW com.cg.domain.dto.service.maintenanceMaintenanceItem.MaintenanceMaintenanceItemResDTO ( " +
                "mmi.id, " +
                "mmi.name, " +
                "mmi.maintenance, " +
                "mmi.maintenanceItem" +
            ") " +
            "FROM MaintenanceMaintenanceItem as mmi " +
            "WHERE mmi.maintenance.id = :maintenanceId"
    )
    List<MaintenanceMaintenanceItemResDTO> findAllByMaintenance_Id (Long maintenanceId);
}