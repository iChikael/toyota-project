package com.cg.service.service.carPlate;

import com.cg.domain.entity.service.ManagementCarPlateCarQueue;
import com.cg.repository.service.carPlate.IManagementCarPlateCarQueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ManagementCarPlateCarQueueServiceImpl implements IManagementCarPlateCarQueueService {

    @Autowired
    private IManagementCarPlateCarQueueRepository managementCarServiceCustomerRepository;

    @Override
    public List<ManagementCarPlateCarQueue> findAll() {
        return managementCarServiceCustomerRepository.findAll();
    }

    @Override
    public Optional<ManagementCarPlateCarQueue> findById(Long id) {
        return managementCarServiceCustomerRepository.findById(id);
    }

    @Override
    public ManagementCarPlateCarQueue save(ManagementCarPlateCarQueue managementCarPlateCarQueue) {
        return managementCarServiceCustomerRepository.save(managementCarPlateCarQueue);
    }

    @Override
    public void delete(ManagementCarPlateCarQueue managementCarPlateCarQueue) {

    }

    @Override
    public void deleteById(Long id) {
    }
}
