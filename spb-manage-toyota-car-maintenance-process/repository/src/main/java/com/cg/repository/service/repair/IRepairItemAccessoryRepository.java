package com.cg.repository.service.repair;

import com.cg.domain.dto.service.repairItemAccessory.RepairItemAccessoryResDTO;
import com.cg.domain.entity.service.repair.RepairItem;
import com.cg.domain.entity.service.repair.RepairItemAccessory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRepairItemAccessoryRepository extends JpaRepository<RepairItemAccessory, Long> {

    @Query("SELECT NEW com.cg.domain.dto.service.repairItemAccessory.RepairItemAccessoryResDTO ( " +
                "mia.id, " +
                "mia.accessory, " +
                "mia.repairItem " +
            ") " +
            "FROM RepairItemAccessory AS mia " +
            "WHERE mia.repairItem.id = :repairItemId"
    )
    List<RepairItemAccessoryResDTO> findAllByRepairItemId (Long repairItemId);

    List<RepairItemAccessory> findAllByRepairItem (RepairItem repairItem);

    RepairItemAccessory findByIdAndRepairItem(Long id, RepairItem repairItem);
}
