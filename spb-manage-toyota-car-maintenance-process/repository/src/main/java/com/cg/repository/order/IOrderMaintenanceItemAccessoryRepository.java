package com.cg.repository.order;

import com.cg.domain.dto.service.orderService.orderMaintenanceItemAccessory.OrderMaintenanceItemAccessoryResDTO;
import com.cg.domain.entity.orderService.OrderMaintenanceItem;
import com.cg.domain.entity.orderService.OrderMaintenanceItemAccessory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IOrderMaintenanceItemAccessoryRepository extends JpaRepository<OrderMaintenanceItemAccessory, Long> {
    @Query("SELECT NEW com.cg.domain.dto.service.orderService.orderMaintenanceItemAccessory.OrderMaintenanceItemAccessoryResDTO( " +
                "odmiac.id, " +
                "odmiac.accessoryName, " +
                "odmiac.accessoryUnit, " +
                "odmiac.accessoryQuantity, " +
                "odmiac.accessory " +
            ") " +
            "FROM OrderMaintenanceItemAccessory as odmiac " +
            "WHERE odmiac.orderMaintenanceItem.id = :orderMaintenanceItemId"
    )
    List<OrderMaintenanceItemAccessoryResDTO> findAllOrderMaintenanceItemAccessoryResDTOByOrderMaintenanceItemId (@Param("orderMaintenanceItemId") Long orderMaintenanceItemId);

    List<OrderMaintenanceItemAccessory> findAllByOrderMaintenanceItem (OrderMaintenanceItem orderMaintenanceItem);

    List<OrderMaintenanceItemAccessory> findAllByOrderMaintenanceItemAndDeletedIsFalse(OrderMaintenanceItem orderMaintenanceItem);
}
