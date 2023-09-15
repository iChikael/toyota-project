package com.cg.repository.service.maintenance;

import com.cg.domain.dto.service.maintaince.MaintenanceResDTO;
import com.cg.domain.entity.service.maintenance.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface IMaintenanceRepository extends JpaRepository<Maintenance, Long> {
    @Query("SELECT NEW com.cg.domain.dto.service.maintaince.MaintenanceResDTO ( " +
                "mtn.id, " +
                "mtn.code, " +
                "mtn.title, " +
                "mtn.unitWage, " +
                "mtn.priceWage, " +
                "mtn.quantityWage   " +
            ") " +
            "FROM Maintenance as mtn " +
            "WHERE mtn.deleted = false"
    )
    List<MaintenanceResDTO> findAllMaintenanceResDTO();

    Optional<Maintenance> findByIdAndDeletedIsFalse(Long id);
}
