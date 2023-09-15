package com.cg.service.storage;

import com.cg.domain.entity.storage.Supplier;
import com.cg.repository.storage.ISupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class SupplierServiceImpl implements ISupplierService{

    @Autowired
    private ISupplierRepository supplierRepository;

    @Override
    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    @Override
    public Optional<Supplier> findById(String id) {
        return supplierRepository.findById(Long.valueOf(id));
    }

    @Override
    public Supplier save(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Override
    public void delete(Supplier supplier) {

    }

    @Override
    public void deleteById(String id) {

    }
}
