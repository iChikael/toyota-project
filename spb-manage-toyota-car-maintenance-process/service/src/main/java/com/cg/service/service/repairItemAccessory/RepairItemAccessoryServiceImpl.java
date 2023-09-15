package com.cg.service.service.repairItemAccessory;

import com.cg.domain.dto.service.repairItemAccessory.RepairItemAccessoryResDTO;
import com.cg.domain.entity.service.repair.RepairItemAccessory;
import com.cg.repository.service.repair.IRepairItemAccessoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class RepairItemAccessoryServiceImpl implements IRepairItemAccessoryService {

    @Autowired
    private IRepairItemAccessoryRepository repairItemAccessoryRepository;

    @Override
    public List<RepairItemAccessory> findAll() {
        return repairItemAccessoryRepository.findAll();
    }

    @Override
    public Optional<RepairItemAccessory> findById(Long id) {
        return repairItemAccessoryRepository.findById(id);
    }

    @Override
    public RepairItemAccessory save(RepairItemAccessory repairItemAccessory) {
        return repairItemAccessoryRepository.save(repairItemAccessory);
    }

    @Override
    public void delete(RepairItemAccessory repairItemAccessory) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<RepairItemAccessoryResDTO> findAllByRepairItemId(Long repairItemId) {
        return repairItemAccessoryRepository.findAllByRepairItemId(repairItemId);
    }
}
