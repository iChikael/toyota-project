package com.cg.service.order;

import com.cg.domain.dto.service.orderService.orderMaintenanceItemAccessory.OrderMaintenanceItemAccessoryResDTO;
import com.cg.domain.entity.orderService.OrderMaintenanceItem;
import com.cg.domain.entity.orderService.OrderMaintenanceItemAccessory;
import com.cg.service.IGeneralService;

import java.util.List;

public interface IOrderMaintenanceItemAccessoryService extends IGeneralService<OrderMaintenanceItemAccessory, Long> {

    List<OrderMaintenanceItemAccessoryResDTO> findAllOrderMaintenanceItemAccessoryResDTOByOrderMaintenanceItemId (Long orderMaintenanceItemId);
    List<OrderMaintenanceItemAccessory> findAllByOrderMaintenanceItem (OrderMaintenanceItem orderMaintenanceItem);

}
