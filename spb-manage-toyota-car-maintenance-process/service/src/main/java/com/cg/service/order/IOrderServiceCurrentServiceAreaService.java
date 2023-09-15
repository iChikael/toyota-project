package com.cg.service.order;

import com.cg.domain.dto.service.orderService.orderService.IOrderServiceCurrentServiceAreaResDTO;
import com.cg.domain.dto.service.orderService.orderService.OrderServiceCurrentServiceAreaResDTO;
import com.cg.domain.dto.service.orderService.orderService.OrderServiceCurrentServiceAreaUpdateReqDTO;
import com.cg.domain.entity.orderService.OrderServiceCurrentServiceArea;
import com.cg.service.IGeneralService;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IOrderServiceCurrentServiceAreaService extends IGeneralService<OrderServiceCurrentServiceArea, Long> {
    Optional<OrderServiceCurrentServiceArea> findByOrderServiceIdAndDeletedIsFalse (Long orderServiceId);

    List<IOrderServiceCurrentServiceAreaResDTO> findByOrderServiceIdAndTimeCreate (Long orderServiceId);

    Optional<IOrderServiceCurrentServiceAreaResDTO> findByOrderServiceIdAndTimeDone (Long orderServiceId);

    OrderServiceCurrentServiceArea updateServiceAreaName(OrderServiceCurrentServiceAreaUpdateReqDTO orderServiceCurrentServiceAreaUpdateReqDTO);
}
