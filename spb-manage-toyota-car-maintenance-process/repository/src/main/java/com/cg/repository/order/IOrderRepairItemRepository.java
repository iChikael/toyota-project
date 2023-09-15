package com.cg.repository.order;

import com.cg.domain.dto.service.orderService.orderRepairItem.OrderRepairItemResDTO;
import com.cg.domain.entity.orderService.OrderRepairItem;
import com.cg.domain.entity.orderService.OrderService;
import com.cg.domain.entity.service.ServiceArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepairItemRepository extends JpaRepository<OrderRepairItem, Long> {

    @Query("SELECT NEW com.cg.domain.dto.service.orderService.orderRepairItem.OrderRepairItemResDTO( " +
                "odri.id, " +
                "odri.name, " +
                "odri.packName, " +
                "odri.payment, " +
                "odri.unitWage, " +
                "odri.quantity, " +
                "odri.priceWage, " +
                "odri.fees, " +
                "odri.discount, " +
                "odri.amount, " +
                "odri.status, " +
                "odri.repairItem " +
            ") " +
            "FROM OrderRepairItem as odri " +
            "WHERE odri.orderService.id = :orderServiceId"
    )
    List<OrderRepairItemResDTO> findAllOrderRepairItemResDTOByOrderServiceId(@Param("orderServiceId") Long orderServiceId);

    @Query("SELECT NEW com.cg.domain.dto.service.orderService.orderRepairItem.OrderRepairItemResDTO( " +
                "odri.id, " +
                "odri.name, " +
                "odri.packName, " +
                "odri.payment, " +
                "odri.unitWage, " +
                "odri.quantity, " +
                "odri.priceWage, " +
                "odri.fees, " +
                "odri.discount, " +
                "odri.amount, " +
                "odri.status, " +
                "odri.repairItem " +
            ") " +
            "FROM OrderRepairItem as odri " +
            "WHERE odri.orderService.id = :orderServiceId " +
            "AND odri.repairItem.serviceArea.id = :serviceAreaId"
    )
    List<OrderRepairItemResDTO> findAllRepairItemByOrderServiceIdAndServiceAreaId(@Param("orderServiceId") Long orderServiceId, @Param("serviceAreaId") Long serviceAreaId);

    List<OrderRepairItem> findAllByOrderService (OrderService orderService);

    List<OrderRepairItem> findAllByOrderServiceAndRepairItemServiceArea (OrderService orderService, ServiceArea serviceArea);

    List<OrderRepairItem> findAllByOrderServiceAndDeletedIsFalse(OrderService orderService);

}
