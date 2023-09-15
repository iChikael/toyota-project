package com.cg.repository.service.maintenance;

import com.cg.domain.dto.service.maintenanceItemAccessory.MaintenanceItemAccessoryResDTO;
import com.cg.domain.entity.service.maintenance.MaintenanceItem;
import com.cg.domain.entity.service.maintenance.MaintenanceItemAccessory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IMaintenanceItemAccessoryRepository extends JpaRepository <MaintenanceItemAccessory, Long> {

    @Query("SELECT NEW com.cg.domain.dto.service.maintenanceItemAccessory.MaintenanceItemAccessoryResDTO ( " +
                "mia.id, " +
                "mia.accessory, " +
                "mia.maintenanceItem " +
            ") " +
            "FROM MaintenanceItemAccessory AS mia " +
            "WHERE mia.maintenanceItem.id = :maintenanceItemId"
    )
    List<MaintenanceItemAccessoryResDTO> findAllByMaintenanceItem_Id (Long maintenanceItemId);

    List<MaintenanceItemAccessory> findAllByMaintenanceItem (MaintenanceItem maintenanceItem);
}
