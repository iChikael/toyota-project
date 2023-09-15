package com.cg.service.order;

import com.cg.domain.dto.service.orderService.orderMaintenanceItem.OrderMaintenanceItemReqDTO;
import com.cg.domain.dto.service.orderService.orderMaintenanceItem.OrderMaintenanceItemResDTO;
import com.cg.domain.dto.service.orderService.orderMaintenanceItem.OrderMaintenanceItemUpdateReqDTO;
import com.cg.domain.dto.service.orderService.orderMaintenanceItem.OrderMaintenanceItemUpdateStatusReqDTO;
import com.cg.domain.entity.orderService.OrderMaintenance;
import com.cg.domain.entity.orderService.OrderMaintenanceItem;
import com.cg.service.IGeneralService;

import java.util.List;

public interface IOrderMaintenanceItemService extends IGeneralService<OrderMaintenanceItem, Long> {

    List<OrderMaintenanceItemResDTO> updateStatus (OrderMaintenanceItemUpdateReqDTO orderMaintenanceItemUpdateReqDTO);
    List<OrderMaintenanceItem> saveAll(List<OrderMaintenanceItem> orderMaintenanceItemList);

    List<OrderMaintenanceItemResDTO> findAllOrderMaintenanceItemResDTOByOrderMaintenanceId (Long orderMaintenanceId);
    List<OrderMaintenanceItem> findAllByOrderMaintenance (OrderMaintenance orderMaintenance);

    List<OrderMaintenanceItemResDTO> findAllOrderMaintenanceItemByServiceAreaIdResDTO(OrderMaintenanceItemReqDTO orderMaintenanceItemReqDTO);

    List<OrderMaintenanceItemResDTO> updateStatusDone(OrderMaintenanceItemUpdateStatusReqDTO orderMaintenanceItemUpdateStatusReqDTO);

    List<OrderMaintenanceItemResDTO> updateStatusDoneAll(OrderMaintenanceItemUpdateStatusReqDTO orderMaintenanceItemUpdateStatusReqDTO);
}
