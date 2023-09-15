package com.cg.service.staff;

import com.cg.domain.entity.staff.StaffLocation;
import com.cg.repository.staff.IStaffLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class StaffLocationServiceImpl implements IStaffLocationService {

    @Autowired
    private IStaffLocationRepository staffLocationRepository;

    @Override
    public List<StaffLocation> findAll() {
        return staffLocationRepository.findAll();
    }

    @Override
    public Optional<StaffLocation> findById(Long id) {
        return staffLocationRepository.findById(Long.valueOf(id));
    }

    @Override
    public StaffLocation save(StaffLocation staffLocation) {
        return staffLocationRepository.save(staffLocation);
    }

    @Override
    public void delete(StaffLocation staffLocation) {

    }

    @Override
    public void deleteById(Long id) {

    }

}
