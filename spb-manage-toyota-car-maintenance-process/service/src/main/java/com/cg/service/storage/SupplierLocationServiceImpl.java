package com.cg.service.storage;

import com.cg.domain.entity.storage.SupplierLocation;
import com.cg.repository.storage.ISupplierLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class SupplierLocationServiceImpl implements ISupplierLocationService{

    @Autowired
    private ISupplierLocationRepository supplierLocationRepository;

    @Override
    public List<SupplierLocation> findAll() {
        return supplierLocationRepository.findAll();
    }

    @Override
    public Optional<SupplierLocation> findById(String id) {
        return supplierLocationRepository.findById(Long.valueOf(id));
    }

    @Override
    public SupplierLocation save(SupplierLocation supplierLocation) {
        return supplierLocationRepository.save(supplierLocation);
    }

    @Override
    public void delete(SupplierLocation supplierLocation) {

    }

    @Override
    public void deleteById(String id) {

    }
}
