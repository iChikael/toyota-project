package com.cg.service.order;

import com.cg.domain.dto.service.orderService.orderRepairItem.OrderRepairItemReqDTO;
import com.cg.domain.dto.service.orderService.orderRepairItem.OrderRepairItemUpdateReqDTO;
import com.cg.domain.dto.service.orderService.orderRepairItem.OrderRepairItemUpdateStatusReqDTO;
import com.cg.domain.dto.service.orderService.orderService.OrderRepairDTO;
import com.cg.domain.dto.service.orderService.orderRepairItem.OrderRepairItemResDTO;
import com.cg.domain.entity.orderService.OrderRepairItem;
import com.cg.domain.entity.orderService.OrderService;
import com.cg.domain.entity.service.repair.RepairItem;
import com.cg.service.IGeneralService;

import java.util.List;

public interface IOrderRepairItemService extends IGeneralService<OrderRepairItem, Long> {

    List<OrderRepairItem> saveAll (List<OrderRepairItem> orderRepairItemList);
    List<OrderRepairItemResDTO> findAllOrderRepairItemResDTOByOrderServiceId(Long orderServiceId);

    OrderRepairItem create(RepairItem repairItem, OrderRepairDTO orderRepairDTO, OrderService orderService);

    List<OrderRepairItem> findAllByOrderService(OrderService orderService);

    OrderRepairItem update(OrderRepairItem orderRepairItem, OrderRepairDTO orderRepairDTO);

    List<OrderRepairItemResDTO> updateStatus(OrderRepairItemUpdateReqDTO orderMaintenanceItemUpdateReqDTO);

    List<OrderRepairItemResDTO> findAllOrderRepairItemByServiceAreaIdResDTO(OrderRepairItemReqDTO orderRepairItemReqDTO);

    List<OrderRepairItemResDTO> updateStatusDone(OrderRepairItemUpdateStatusReqDTO orderRepairItemUpdateStatusReqDTO);

    List<OrderRepairItemResDTO> updateStatusDoneAll(OrderRepairItemUpdateStatusReqDTO orderRepairItemUpdateStatusReqDTO);
}
