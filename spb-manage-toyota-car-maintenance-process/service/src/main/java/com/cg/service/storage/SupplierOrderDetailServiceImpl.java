package com.cg.service.storage;

import com.cg.domain.entity.storage.SupplierOrderDetail;
import com.cg.repository.storage.ISupplierOrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SupplierOrderDetailServiceImpl implements ISupplierOrderDetailService {

    @Autowired
    private ISupplierOrderDetailRepository supplierOrderDetailRepository;

    @Override
    public List<SupplierOrderDetail> findAll() {
        return supplierOrderDetailRepository.findAll();
    }

    @Override
    public Optional<SupplierOrderDetail> findById(String id) {
        return supplierOrderDetailRepository.findById(Long.valueOf(id));
    }

    @Override
    public SupplierOrderDetail save(SupplierOrderDetail supplierOrderDetail) {
        return supplierOrderDetailRepository.save(supplierOrderDetail);
    }

    @Override
    public void delete(SupplierOrderDetail supplierOrderDetail) {

    }

    @Override
    public void deleteById(String id) {

    }
}
