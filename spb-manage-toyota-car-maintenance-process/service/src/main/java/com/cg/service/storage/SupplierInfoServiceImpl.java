package com.cg.service.storage;

import com.cg.domain.entity.storage.SupplierInfo;
import com.cg.repository.storage.ISupplierInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SupplierInfoServiceImpl implements ISupplierInfoService {

    @Autowired
    private ISupplierInfoRepository supplierInfoRepository;

    @Override
    public List<SupplierInfo> findAll() {
        return supplierInfoRepository.findAll();
    }

    @Override
    public Optional<SupplierInfo> findById(Long id) {
        return supplierInfoRepository.findById(id);
    }

    @Override
    public SupplierInfo save(SupplierInfo supplierInfo) {
        return supplierInfoRepository.save(supplierInfo);
    }

    @Override
    public void delete(SupplierInfo supplierInfo) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
