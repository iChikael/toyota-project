package com.cg.repository.service.maintenance;

import com.cg.domain.dto.service.maintenanceItem.MaintenanceItemResDTO;
import com.cg.domain.entity.service.maintenance.MaintenanceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface IMaintenanceItemRepository extends JpaRepository <MaintenanceItem, Long> {

    @Query("SELECT NEW com.cg.domain.dto.service.maintenanceItem.MaintenanceItemResDTO ( " +
                "mtni.id, " +
                "mtni.code, " +
                "mtni.title, " +
                "mtni.serviceArea " +
            ") " +
            "FROM MaintenanceItem AS mtni " +
            "WHERE mtni.deleted = false"
    )
    List<MaintenanceItemResDTO> findAllMaintenanceItemResDTO();

    Optional<MaintenanceItem> findByIdAndDeletedIsFalse(Long id);

}
