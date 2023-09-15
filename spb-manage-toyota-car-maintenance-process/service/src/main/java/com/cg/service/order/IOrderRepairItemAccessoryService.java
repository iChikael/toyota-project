package com.cg.service.order;

import com.cg.domain.dto.service.orderService.orderRepairItemAccessory.OrderRepairItemAccessoryResDTO;
import com.cg.domain.entity.orderService.OrderRepairItem;
import com.cg.domain.entity.orderService.OrderRepairItemAccessory;
import com.cg.service.IGeneralService;

import java.util.List;

public interface IOrderRepairItemAccessoryService extends IGeneralService <OrderRepairItemAccessory, Long> {
    List<OrderRepairItemAccessoryResDTO> findAllOrderRepairItemAccessoryResDTOByOrderRepairItemId (Long orderRepairItemId);
    List<OrderRepairItemAccessory> findAllByOrderRepairItem(OrderRepairItem orderRepairItem);

}
