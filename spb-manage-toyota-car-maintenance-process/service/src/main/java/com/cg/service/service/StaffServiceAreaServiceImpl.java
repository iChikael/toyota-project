package com.cg.service.service;

import com.cg.domain.entity.service.StaffServiceArea;
import com.cg.repository.service.serviceArea.IStaffServiceAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StaffServiceAreaServiceImpl implements IStaffServiceAreaService {

    @Autowired
    private IStaffServiceAreaRepository staffServiceAreaRepository;

    @Override
    public List<StaffServiceArea> findAll() {
        return staffServiceAreaRepository.findAll();
    }

    @Override
    public Optional<StaffServiceArea> findById(Long id) {
        return staffServiceAreaRepository.findById(id);
    }

    @Override
    public StaffServiceArea save(StaffServiceArea staffServiceArea) {
        return staffServiceAreaRepository.save(staffServiceArea);
    }

    @Override
    public void delete(StaffServiceArea staffServiceArea) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
