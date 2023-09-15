package com.cg.service.order;

import com.cg.domain.dto.service.orderService.orderService.IOrderServiceCurrentServiceAreaResDTO;
import com.cg.domain.dto.service.orderService.orderService.OrderServiceCurrentServiceAreaResDTO;
import com.cg.domain.dto.service.orderService.orderService.OrderServiceCurrentServiceAreaUpdateReqDTO;
import com.cg.domain.entity.orderService.OrderServiceCurrentServiceArea;
import com.cg.domain.entity.service.ServiceArea;
import com.cg.repository.order.IOrderServiceCurrentServiceAreaRepository;
import com.cg.repository.service.serviceArea.IServiceAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceCurrentServiceAreaServiceImpl implements IOrderServiceCurrentServiceAreaService{

    @Autowired
    private IOrderServiceCurrentServiceAreaRepository orderServiceCurrentServiceAreaRepository;

    @Autowired
    private IServiceAreaRepository serviceAreaRepository;

    @Override
    public List<OrderServiceCurrentServiceArea> findAll() {
        return orderServiceCurrentServiceAreaRepository.findAll();
    }

    @Override
    public Optional<OrderServiceCurrentServiceArea> findById(Long id) {
        return orderServiceCurrentServiceAreaRepository.findById(id);
    }

    @Override
    public OrderServiceCurrentServiceArea save(OrderServiceCurrentServiceArea orderServiceCurrentServiceArea) {
        return orderServiceCurrentServiceAreaRepository.save(orderServiceCurrentServiceArea);
    }

    @Override
    public void delete(OrderServiceCurrentServiceArea orderServiceCurrentServiceArea) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Optional<OrderServiceCurrentServiceArea> findByOrderServiceIdAndDeletedIsFalse(Long orderServiceId) {
        return orderServiceCurrentServiceAreaRepository.findByOrderServiceIdAndDeletedIsFalse(orderServiceId);
    }

    @Override
    public List<IOrderServiceCurrentServiceAreaResDTO> findByOrderServiceIdAndTimeCreate(Long orderServiceId) {
        return orderServiceCurrentServiceAreaRepository.findByOrderServiceIdAndTimeCreate(orderServiceId);
    }

    @Override
    public Optional<IOrderServiceCurrentServiceAreaResDTO> findByOrderServiceIdAndTimeDone(Long orderServiceId) {
        return orderServiceCurrentServiceAreaRepository.findByOrderServiceIdAndTimeDone(orderServiceId);
    }

    @Override
    public OrderServiceCurrentServiceArea updateServiceAreaName(OrderServiceCurrentServiceAreaUpdateReqDTO orderServiceCurrentServiceAreaUpdateReqDTO) {

        Optional<OrderServiceCurrentServiceArea> orderServiceCurrentServiceAreaOptional = orderServiceCurrentServiceAreaRepository.findByOrderServiceIdAndDeletedIsFalse(Long.parseLong(orderServiceCurrentServiceAreaUpdateReqDTO.getOrderServiceId()));

        if(orderServiceCurrentServiceAreaOptional.isPresent()) {
            OrderServiceCurrentServiceArea orderServiceCurrentServiceArea = orderServiceCurrentServiceAreaOptional.get();
            orderServiceCurrentServiceArea.setDeleted(true);
            orderServiceCurrentServiceAreaRepository.save(orderServiceCurrentServiceArea);

            OrderServiceCurrentServiceArea orderServiceCurrentServiceAreaNew = new OrderServiceCurrentServiceArea();
            orderServiceCurrentServiceAreaNew.setId(null);
            orderServiceCurrentServiceAreaNew.setOrderService(orderServiceCurrentServiceArea.getOrderService());

            Optional<ServiceArea> serviceAreaOptional = serviceAreaRepository.findByIdAndDeletedIsFalse(Long.parseLong(orderServiceCurrentServiceAreaUpdateReqDTO.getServiceAreaId()));

            if (serviceAreaOptional.isEmpty()) {
                orderServiceCurrentServiceAreaNew.setServiceAreaName(null);
            } else {
                orderServiceCurrentServiceAreaNew.setServiceAreaName(serviceAreaOptional.get().getName());
            }

            orderServiceCurrentServiceAreaRepository.save(orderServiceCurrentServiceAreaNew);

            return orderServiceCurrentServiceAreaNew;
        }

        return null;
    }
}
