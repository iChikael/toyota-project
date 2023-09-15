package com.cg.repository.order;

import com.cg.domain.dto.service.orderService.orderRepairItemAccessory.OrderRepairItemAccessoryResDTO;
import com.cg.domain.entity.orderService.OrderRepairItem;
import com.cg.domain.entity.orderService.OrderRepairItemAccessory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  IOrderRepairItemAccessoryRepository extends JpaRepository <OrderRepairItemAccessory, Long> {
    @Query("SELECT NEW com.cg.domain.dto.service.orderService.orderRepairItemAccessory.OrderRepairItemAccessoryResDTO( " +
                "odriac.id, " +
                "odriac.accessoryName, " +
                "odriac.accessoryUnit, " +
                "odriac.accessoryQuantity, " +
                "odriac.accessory " +
            ") " +
            "FROM OrderRepairItemAccessory as odriac " +
            "WHERE odriac.orderRepairItem.id = :orderRepairItemId"
    )
    List<OrderRepairItemAccessoryResDTO> findAllOrderRepairItemAccessoryResDTOByOrderRepairItemId (@Param("orderRepairItemId") Long orderRepairItemId);

    List<OrderRepairItemAccessory> findAllByOrderRepairItem(OrderRepairItem orderRepairItem);

    List<OrderRepairItemAccessory> findAllByOrderRepairItemAndDeletedIsFalse(OrderRepairItem orderRepairItem);
}
