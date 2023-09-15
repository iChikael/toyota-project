package com.cg.repository.order;


import com.cg.domain.dto.service.orderService.orderMaintenanceItem.OrderMaintenanceItemResDTO;
import com.cg.domain.entity.orderService.OrderMaintenance;
import com.cg.domain.entity.orderService.OrderMaintenanceItem;
import com.cg.domain.entity.service.ServiceArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderMaintenanceItemRepository extends JpaRepository<OrderMaintenanceItem, Long> {

    @Query("SELECT NEW com.cg.domain.dto.service.orderService.orderMaintenanceItem.OrderMaintenanceItemResDTO( " +
                "odmi.id, " +
                "odmi.serviceItemName, " +
                "odmi.checklistName, " +
                "odmi.status, " +
                "odmi.maintenanceItem " +
            ") " +
            "FROM OrderMaintenanceItem as odmi " +
            "WHERE odmi.orderMaintenance.id = :orderMaintenanceId"
    )
    List<OrderMaintenanceItemResDTO> findAllOrderMaintenanceItemResDTOByOrderMaintenanceId (@Param("orderMaintenanceId") Long orderMaintenanceId);

    @Query("SELECT NEW com.cg.domain.dto.service.orderService.orderMaintenanceItem.OrderMaintenanceItemResDTO( " +
                "odmi.id, " +
                "odmi.serviceItemName, " +
                "odmi.checklistName, " +
                "odmi.status, " +
                "odmi.maintenanceItem " +
            ") " +
            "FROM OrderMaintenanceItem as odmi " +
            "WHERE odmi.orderMaintenance.id = :orderMaintenanceId " +
            "AND odmi.maintenanceItem.serviceArea.id = :serviceAreaId"
    )
    List<OrderMaintenanceItemResDTO> findAllOrderMaintenanceItemResDTOByOrderMaintenanceIdAndServiceAreaId (@Param("orderMaintenanceId") Long orderMaintenanceId, @Param("serviceAreaId") Long serviceAreaId);

    List<OrderMaintenanceItem> findAllByOrderMaintenanceAndMaintenanceItemServiceArea (OrderMaintenance orderMaintenance, ServiceArea serviceArea);

    List<OrderMaintenanceItem> findAllByOrderMaintenance (OrderMaintenance orderMaintenance);

    List<OrderMaintenanceItem> findAllByOrderMaintenanceAndDeletedIsFalse(OrderMaintenance orderMaintenance);
}
