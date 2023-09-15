package com.cg.service.order;

import com.cg.domain.dto.service.orderService.orderService.*;
import com.cg.domain.entity.orderService.OrderService;
import com.cg.domain.entity.service.CarQueue;
import com.cg.domain.enums.EStatusCarQueue;
import com.cg.domain.enums.EStatusOrderService;
import com.cg.service.IGeneralService;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface IOrderServiceService extends IGeneralService<OrderService, Long> {
    OrderServiceResDTO getOrderServiceResDTOByIdAndStatus(Long id, EStatusOrderService status);

    List<OrderServiceResDTO> findAllOrderServiceByStatusResDTO(EStatusOrderService status);

    List<OrderServiceResDTO> findAllByStatusCarQueueDoneAndDeletedIsFalse(EStatusCarQueue eStatusCarQueue);

    OrderService createOrderService(OrderServiceCreateReqDTO orderServiceCreateReqDTO, CarQueue carQueue) throws ParseException;

    Optional<OrderService> findByIdAndDeletedIsFalse(Long id);

    Optional<OrderService> findByCarQueueAndDeletedIsFalse(CarQueue carQueue);

    OrderService update(OrderService orderService, OrderServiceUpdateReqDTO orderServiceUpdateReqDTO);

    List<OrderServiceResDTO> findAllOrderServiceByServiceAreaNameAndDeletedIsFalseResDTO(String serviceAreaName);

    void updateStatusCarQueue(OrderServiceUpdateStatusCarQueueReqDTO orderServiceUpdateStatusCarQueueReqDTO);

}
