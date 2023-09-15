package com.cg.repository.service.repair;

import com.cg.domain.dto.service.repairItem.RepairItemResDTO;
import com.cg.domain.entity.service.repair.RepairItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRepairItemRepository extends JpaRepository<RepairItem, Long> {

    Optional<RepairItem> findByIdAndDeletedIsFalse(Long id);

    @Query("SELECT NEW com.cg.domain.dto.service.repairItem.RepairItemResDTO ( " +
                "reI.id, " +
                "reI.code, " +
                "reI.title, " +
                "reI.serviceArea " +
            ") " +
            "FROM RepairItem as reI " +
            "WHERE reI.deleted = false"
    )
    List<RepairItemResDTO> findAllRepairItemResDTO();
}
