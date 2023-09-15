package com.cg.service.storage;

import com.cg.domain.entity.storage.DeliveryBillDetail;
import com.cg.repository.storage.IDeliveryBillDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class DeliveryBillDetailServiceImpl implements IDeliveryBillDetailService{

    @Autowired
    private IDeliveryBillDetailRepository deliveryBillDetailRepository;

    @Override
    public List<DeliveryBillDetail> findAll() {
        return deliveryBillDetailRepository.findAll();
    }

    @Override
    public Optional<DeliveryBillDetail> findById(String id) {
        return deliveryBillDetailRepository.findById(Long.valueOf(id));
    }

    @Override
    public DeliveryBillDetail save(DeliveryBillDetail deliveryBillDetail) {
        return deliveryBillDetailRepository.save(deliveryBillDetail);
    }

    @Override
    public void delete(DeliveryBillDetail deliveryBillDetail) {

    }

    @Override
    public void deleteById(String id) {

    }
}
