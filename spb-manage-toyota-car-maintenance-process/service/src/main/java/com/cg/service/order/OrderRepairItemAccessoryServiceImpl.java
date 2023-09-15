package com.cg.service.order;

import com.cg.domain.dto.service.orderService.orderRepairItemAccessory.OrderRepairItemAccessoryResDTO;
import com.cg.domain.entity.orderService.OrderRepairItem;
import com.cg.domain.entity.orderService.OrderRepairItemAccessory;
import com.cg.repository.order.IOrderRepairItemAccessoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderRepairItemAccessoryServiceImpl implements IOrderRepairItemAccessoryService {

    @Autowired
    private IOrderRepairItemAccessoryRepository orderRepairItemAccessoryRepository;

    @Override
    public List<OrderRepairItemAccessory> findAll() {
        return orderRepairItemAccessoryRepository.findAll();
    }

    @Override
    public Optional<OrderRepairItemAccessory> findById(Long id) {
        return orderRepairItemAccessoryRepository.findById(id);
    }

    @Override
    public OrderRepairItemAccessory save(OrderRepairItemAccessory orderRepairItemAccessory) {
        return orderRepairItemAccessoryRepository.save(orderRepairItemAccessory);
    }

    @Override
    public void delete(OrderRepairItemAccessory orderRepairItemAccessory) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<OrderRepairItemAccessoryResDTO> findAllOrderRepairItemAccessoryResDTOByOrderRepairItemId(Long orderRepairItemId) {
        return orderRepairItemAccessoryRepository.findAllOrderRepairItemAccessoryResDTOByOrderRepairItemId (orderRepairItemId);
    }

    @Override
    public List<OrderRepairItemAccessory> findAllByOrderRepairItem(OrderRepairItem orderRepairItem) {
        return orderRepairItemAccessoryRepository.findAllByOrderRepairItem(orderRepairItem);
    }

}
