package com.cg.service.order;

import com.cg.domain.dto.service.orderService.orderMaintenanceItemAccessory.OrderMaintenanceItemAccessoryResDTO;
import com.cg.domain.entity.orderService.OrderMaintenanceItem;
import com.cg.domain.entity.orderService.OrderMaintenanceItemAccessory;
import com.cg.repository.order.IOrderMaintenanceItemAccessoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class OrderMaintenanceItemAccessoryServiceImpl implements IOrderMaintenanceItemAccessoryService {

    @Autowired
    private IOrderMaintenanceItemAccessoryRepository orderMaintenanceItemAccessoryRepository;

    @Override
    public List<OrderMaintenanceItemAccessory> findAll() {
        return orderMaintenanceItemAccessoryRepository.findAll();
    }

    @Override
    public Optional<OrderMaintenanceItemAccessory> findById(Long id) {
        return orderMaintenanceItemAccessoryRepository.findById(id);
    }

    @Override
    public OrderMaintenanceItemAccessory save(OrderMaintenanceItemAccessory orderMaintenanceItemAccessory) {
        return orderMaintenanceItemAccessoryRepository.save(orderMaintenanceItemAccessory);
    }

    @Override
    public void delete(OrderMaintenanceItemAccessory orderMaintenanceItemAccessory) {
        orderMaintenanceItemAccessoryRepository.delete(orderMaintenanceItemAccessory);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<OrderMaintenanceItemAccessoryResDTO> findAllOrderMaintenanceItemAccessoryResDTOByOrderMaintenanceItemId(Long orderMaintenanceItemId) {
        return orderMaintenanceItemAccessoryRepository.findAllOrderMaintenanceItemAccessoryResDTOByOrderMaintenanceItemId (orderMaintenanceItemId);
    }

    @Override
    public List<OrderMaintenanceItemAccessory> findAllByOrderMaintenanceItem(OrderMaintenanceItem orderMaintenanceItem) {
        return orderMaintenanceItemAccessoryRepository.findAllByOrderMaintenanceItem(orderMaintenanceItem);
    }

}
