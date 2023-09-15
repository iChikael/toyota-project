package com.cg.service.storage;

import com.cg.domain.entity.storage.DeliveryBill;
import com.cg.repository.storage.IDeliveryBillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class DeliveryBillServiceImpl implements IDelirevyBillService{

    @Autowired
    private IDeliveryBillRepository deliveryBillRepository;

    @Override
    public List<DeliveryBill> findAll() {
        return deliveryBillRepository.findAll();
    }

    @Override
    public Optional<DeliveryBill> findById(String id) {
        return deliveryBillRepository.findById(Long.valueOf(id));
    }

    @Override
    public DeliveryBill save(DeliveryBill deliveryBill) {
        return deliveryBillRepository.save(deliveryBill);
    }

    @Override
    public void delete(DeliveryBill deliveryBill) {

    }

    @Override
    public void deleteById(String id) {

    }
}
