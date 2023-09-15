package com.cg.repository.order;

import com.cg.domain.dto.service.orderService.orderMaintenance.OrderMaintenanceResDTO;
import com.cg.domain.entity.orderService.OrderMaintenance;
import com.cg.domain.entity.orderService.OrderService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IOrderMaintenanceRepository extends JpaRepository<OrderMaintenance, Long> {
    @Query("SELECT NEW com.cg.domain.dto.service.orderService.orderMaintenance.OrderMaintenanceResDTO( " +
                "odm.id, " +
                "odm.name, " +
                "odm.packName, " +
                "odm.payment, " +
                "odm.unitWage, " +
                "odm.quantity, " +
                "odm.priceWage, " +
                "odm.fees, " +
                "odm.discount, " +
                "odm.amount, " +
                "odm.status, " +
                "odm.maintenance " +
            ") " +
            "FROM OrderMaintenance as odm " +
            "WHERE odm.orderService.id = :orderServiceId"
    )
    List<OrderMaintenanceResDTO> findAllOrderMaintenanceResDTOByOrderServiceId (@Param("orderServiceId") Long orderServiceId);

    @Query("SELECT NEW com.cg.domain.dto.service.orderService.orderMaintenance.OrderMaintenanceResDTO( " +
                "odm.id, " +
                "odm.name, " +
                "odm.packName, " +
                "odm.payment, " +
                "odm.unitWage, " +
                "odm.quantity, " +
                "odm.priceWage, " +
                "odm.fees, " +
                "odm.discount, " +
                "odm.amount, " +
                "odm.status, " +
                "odm.maintenance " +
            ") " +
            "FROM OrderMaintenance as odm " +
            "WHERE odm.orderService.id = :orderServiceId"
    )
    Optional<OrderMaintenanceResDTO> findOrderMaintenanceResDTOByOrderServiceId (@Param("orderServiceId") Long orderServiceId);

    Optional<OrderMaintenance> findByIdAndDeletedIsFalse (Long id);

    Optional<OrderMaintenance> findOrderMaintenanceByOrderService (OrderService orderService);

    List<OrderMaintenance> findAllByOrderServiceAndDeletedIsFalse(OrderService orderService);
}
