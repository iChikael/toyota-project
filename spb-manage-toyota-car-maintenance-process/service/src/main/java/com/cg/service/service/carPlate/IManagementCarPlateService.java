package com.cg.service.service.carPlate;

import com.cg.domain.dto.service.managementCarPlate.ManagementCarPlateCreateReqDTO;
import com.cg.domain.dto.service.managementCarPlate.ManagementCarPlateResDTO;
import com.cg.domain.dto.service.managementCarPlate.ManagementCarPlateUpdateReqDTO;
import com.cg.domain.entity.customer.Customer;
import com.cg.domain.entity.service.ManagementCarPlate;
import com.cg.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface IManagementCarPlateService extends IGeneralService<ManagementCarPlate, Long> {
    Optional<ManagementCarPlateResDTO> findAllManagementCarPlateResDTOByCarPlate( String carPlate);

    Optional<ManagementCarPlateResDTO> findManagementCarPlateByCarPlate(String carPlate);

    Optional<ManagementCarPlate> findManagementCarPlateByIdAndDeletedIsFalse (Long id);

    List<ManagementCarPlateResDTO> findAllManagementCarPlateByKeyWord (String carPlate);

    List<ManagementCarPlateResDTO> findAllManagementCarPlateResDTO();

    Boolean existsManagementCarPlateByCarNumberPlateAndCarIdAndCustomerId (String carPlate, Long carId, String customerId);

    ManagementCarPlate create(ManagementCarPlateCreateReqDTO managementCarPlateCreateReqDTO, Customer customer);

    ManagementCarPlate update(ManagementCarPlate managementCarPlate, Customer customer, ManagementCarPlateUpdateReqDTO managementCarPlateUpdateReqDTO);
}
