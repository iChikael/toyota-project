package com.cg.service.service.maintenance;

import com.cg.domain.dto.service.maintenanceMaintenanceItem.MaintenanceMaintenanceItemResDTO;
import com.cg.domain.entity.service.maintenance.MaintenanceMaintenanceItem;
import com.cg.repository.service.maintenance.IMaintenanceMaintenanceItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MaintenanceMaintenanceItemServiceImpl implements IMaintenanceMaintenanceItemService {

    @Autowired
    private IMaintenanceMaintenanceItemRepository maintenanceMaintenanceItemRepository;

    @Override
    public List<MaintenanceMaintenanceItem> findAll() {
        return maintenanceMaintenanceItemRepository.findAll();
    }

    @Override
    public Optional<MaintenanceMaintenanceItem> findById(Long id) {
        return maintenanceMaintenanceItemRepository.findById(id);
    }

    @Override
    public MaintenanceMaintenanceItem save(MaintenanceMaintenanceItem maintenanceServiceItem) {
        return maintenanceMaintenanceItemRepository.save(maintenanceServiceItem);
    }

    @Override
    public void delete(MaintenanceMaintenanceItem maintenanceServiceItem) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<MaintenanceMaintenanceItemResDTO> findAllByMaintenance_Id(Long maintenanceId) {
        return maintenanceMaintenanceItemRepository.findAllByMaintenance_Id(maintenanceId);
    }
}
