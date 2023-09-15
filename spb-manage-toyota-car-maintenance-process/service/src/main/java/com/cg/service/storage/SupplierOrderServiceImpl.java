package com.cg.service.storage;

import com.cg.domain.entity.storage.SupplierOrder;
import com.cg.repository.storage.ISupplierOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SupplierOrderServiceImpl implements ISupplierOrderService{

    @Autowired
    private ISupplierOrderRepository supplierOrderRepository;

    @Override
    public List<SupplierOrder> findAll() {
        return supplierOrderRepository.findAll();
    }

    @Override
    public Optional<SupplierOrder> findById(String id) {
        return supplierOrderRepository.findById(Long.valueOf(id));
    }

    @Override
    public SupplierOrder save(SupplierOrder supplierOrder) {
        return supplierOrderRepository.save(supplierOrder);
    }

    @Override
    public void delete(SupplierOrder supplierOrder) {

    }

    @Override
    public void deleteById(String id) {

    }
}
