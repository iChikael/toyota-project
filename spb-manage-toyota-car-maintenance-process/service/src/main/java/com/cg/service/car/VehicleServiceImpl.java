package com.cg.service.car;

import com.cg.domain.dto.car.VehicleResDTO;
import com.cg.domain.entity.car.Vehicle;
import com.cg.repository.car.IVehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class VehicleServiceImpl implements IVehicleService {

    @Autowired
    private IVehicleRepository vehicleRepository;

    @Override
    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    @Override
    public Optional<Vehicle> findById(Long id) {
        return vehicleRepository.findById(id);
    }


    @Override
    public Vehicle save(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Override
    public void delete(Vehicle vehicle) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<VehicleResDTO> getAllVehicleResDTO() {
        return vehicleRepository.findAllVehicleResDTO();
    }
}
