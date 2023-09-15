package com.cg.service.service;

import com.cg.domain.dto.service.serviceArea.ServiceAreaCreateReqDTO;
import com.cg.domain.dto.service.serviceArea.ServiceAreaResDTO;
import com.cg.domain.dto.service.serviceArea.ServiceAreaUpdateReqDTO;
import com.cg.domain.entity.service.ServiceArea;
import com.cg.domain.enums.EStatusServiceArea;
import com.cg.repository.service.serviceArea.IServiceAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ServiceAreaServiceImpl implements IServiceAreaService {

    @Autowired
    private IServiceAreaRepository serviceAreaRepository;

    @Override
    public List<ServiceArea> findAll() {
        return serviceAreaRepository.findAll();
    }

    @Override
    public Optional<ServiceArea> findById(Long id) {
        return serviceAreaRepository.findById(id);
    }

    @Override
    public ServiceArea save(ServiceArea serviceArea) {
        return serviceAreaRepository.save(serviceArea);
    }

    @Override
    public void delete(ServiceArea serviceArea) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Optional<ServiceArea> findByIdAndDeletedIsFalse(Long id) {
        return serviceAreaRepository.findByIdAndDeletedIsFalse(id);
    }

    @Override
    public ServiceArea createServiceArea(ServiceAreaCreateReqDTO serviceAreaCreateReqDTO) {

        ServiceArea serviceArea = new ServiceArea();
        serviceArea.setId(null);
        serviceArea.setName(serviceAreaCreateReqDTO.getName());
        serviceArea.setCapacity(serviceAreaCreateReqDTO.getCapacity());
        serviceArea.setCurrentCapacity(0L);
        serviceArea.setStatus(EStatusServiceArea.STATUS_FREE);

        serviceAreaRepository.save(serviceArea);

        return serviceArea;
    }

    @Override
    public ServiceArea updateServiceArea(ServiceArea serviceArea ,ServiceAreaUpdateReqDTO serviceAreaUpdateReqDTO) {
        ServiceArea updateServiceArea = serviceAreaUpdateReqDTO.toServiceArea();
        serviceAreaRepository.save(updateServiceArea);
        return updateServiceArea;
    }

    @Override
    public List<ServiceAreaResDTO> findAllServiceAreasDTO() {
        return serviceAreaRepository.findAllServiceAreasDTO();
    }


}
