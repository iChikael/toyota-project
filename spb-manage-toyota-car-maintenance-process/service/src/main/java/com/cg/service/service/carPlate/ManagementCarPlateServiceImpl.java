package com.cg.service.service.carPlate;

import com.cg.domain.dto.service.managementCarPlate.ManagementCarPlateCreateReqDTO;
import com.cg.domain.dto.service.managementCarPlate.ManagementCarPlateResDTO;
import com.cg.domain.dto.service.managementCarPlate.ManagementCarPlateUpdateReqDTO;
import com.cg.domain.entity.car.Car;
import com.cg.domain.entity.customer.Customer;
import com.cg.domain.entity.service.ManagementCarPlate;
import com.cg.repository.car.ICarRepository;
import com.cg.repository.service.carPlate.IManagementCarPlateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class ManagementCarPlateServiceImpl implements IManagementCarPlateService {

    @Autowired
    private IManagementCarPlateRepository managementCarPlateRepository;

    @Autowired
    private ICarRepository carRepository;

    @Override
    public List<ManagementCarPlate> findAll() {
        return managementCarPlateRepository.findAll();
    }

    @Override
    public Optional<ManagementCarPlate> findById(Long id) {
        return managementCarPlateRepository.findById(id);
    }

    @Override
    public ManagementCarPlate save(ManagementCarPlate managementCarPlate) {
        return managementCarPlateRepository.save(managementCarPlate);
    }

    @Override
    public void delete(ManagementCarPlate managementCarPlate) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Optional<ManagementCarPlateResDTO> findAllManagementCarPlateResDTOByCarPlate(String carPlate) {
        return managementCarPlateRepository.findAllManagementCarPlateResDTOByCarPlate(carPlate);
    }

    @Override
    public Optional<ManagementCarPlateResDTO> findManagementCarPlateByCarPlate(String carPlate) {
        return managementCarPlateRepository.findManagementCarPlateByCarPlate(carPlate);
    }

    @Override
    public Optional<ManagementCarPlate> findManagementCarPlateByIdAndDeletedIsFalse(Long id) {
        return managementCarPlateRepository.findManagementCarPlateByIdAndDeletedIsFalse(id);
    }

    @Override
    public List<ManagementCarPlateResDTO> findAllManagementCarPlateByKeyWord(String carPlate) {
        return managementCarPlateRepository.findAllManagementCarPlateByKeyWord(carPlate);
    }

    @Override
    public List<ManagementCarPlateResDTO> findAllManagementCarPlateResDTO() {
        return managementCarPlateRepository.findAllManagementCarPlateResDTO();
    }

    @Override
    public Boolean existsManagementCarPlateByCarNumberPlateAndCarIdAndCustomerId(String carPlate, Long carId, String customerId) {
        return managementCarPlateRepository.existsManagementCarPlateByCarNumberPlateAndCarIdAndCustomerId(carPlate, carId, customerId);
    }

    @Override
    public ManagementCarPlate create(ManagementCarPlateCreateReqDTO managementCarPlateCreateReqDTO, Customer customer) {

        ManagementCarPlate managementCarPlate = managementCarPlateCreateReqDTO.toManagementCarPlate();

        managementCarPlate.setCustomer(customer);

        Optional<Car> carOptional = carRepository.findById(Long.parseLong(managementCarPlateCreateReqDTO.getCarId()));

        managementCarPlate.setCar(carOptional.get());

        managementCarPlateRepository.save(managementCarPlate);

        return managementCarPlate;
    }

    @Override
    public ManagementCarPlate update(ManagementCarPlate managementCarPlate, Customer customer, ManagementCarPlateUpdateReqDTO managementCarPlateUpdateReqDTO) {

        managementCarPlate.setDeleted(true);

        managementCarPlateRepository.save(managementCarPlate);

        ManagementCarPlate updateManagementCarPlate = new ManagementCarPlate();
        updateManagementCarPlate.setId(null);

        if (managementCarPlate.getCarNumberPlate().equals(managementCarPlateUpdateReqDTO.getCarNumberPlate())) {
            updateManagementCarPlate.setCarNumberPlate(managementCarPlate.getCarNumberPlate());
            updateManagementCarPlate.setCar(managementCarPlate.getCar());
            updateManagementCarPlate.setCustomer(customer);
        }
        else {
            updateManagementCarPlate.setCarNumberPlate(managementCarPlateUpdateReqDTO.getCarNumberPlate());
            updateManagementCarPlate.setCar(managementCarPlate.getCar());
            updateManagementCarPlate.setCustomer(customer);
        }

        managementCarPlateRepository.save(updateManagementCarPlate);

        return updateManagementCarPlate;
    }
}
