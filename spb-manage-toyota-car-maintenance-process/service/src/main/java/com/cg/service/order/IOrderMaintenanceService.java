package com.cg.service.order;

import com.cg.domain.dto.service.orderService.orderMaintenance.OrderMaintenanceUpdateReqDTO;
import com.cg.domain.dto.service.orderService.orderService.OrderMaintenanceDTO;
import com.cg.domain.dto.service.orderService.orderMaintenance.OrderMaintenanceResDTO;
import com.cg.domain.entity.orderService.OrderMaintenance;
import com.cg.domain.entity.orderService.OrderService;
import com.cg.domain.entity.service.maintenance.Maintenance;
import com.cg.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface IOrderMaintenanceService extends IGeneralService <OrderMaintenance, Long> {

    Optional<OrderMaintenanceResDTO> findOrderMaintenanceResDTOByOrderServiceId (Long orderServiceId);

    OrderMaintenanceResDTO updateStatus (OrderMaintenance orderMaintenance, OrderMaintenanceUpdateReqDTO orderMaintenanceUpdateReqDTO);

    List<OrderMaintenance> saveAll (List<OrderMaintenance> orderMaintenanceList);
    List<OrderMaintenanceResDTO> findAllOrderMaintenanceResDTOByOrderServiceId ( Long orderServiceId);
    OrderMaintenance create(Maintenance maintenance, OrderMaintenanceDTO maintenance1, OrderService orderService);

    OrderMaintenance update(OrderMaintenanceDTO maintenance, OrderMaintenance orderMaintenance);

    Optional<OrderMaintenance> findOrderMaintenanceByOrderService(OrderService orderService);
}
