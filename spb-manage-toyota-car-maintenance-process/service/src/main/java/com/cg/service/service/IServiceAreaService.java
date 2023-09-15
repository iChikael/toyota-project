package com.cg.service.service;

import com.cg.domain.dto.service.serviceArea.ServiceAreaResDTO;
import com.cg.domain.dto.service.serviceArea.ServiceAreaCreateReqDTO;
import com.cg.domain.dto.service.serviceArea.ServiceAreaUpdateReqDTO;
import com.cg.domain.entity.service.ServiceArea;
import com.cg.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface IServiceAreaService extends IGeneralService <ServiceArea, Long> {

    Optional<ServiceArea> findByIdAndDeletedIsFalse (Long id);

    ServiceArea createServiceArea(ServiceAreaCreateReqDTO serviceAreaCreateReqDTO);

    ServiceArea updateServiceArea(ServiceArea serviceArea ,ServiceAreaUpdateReqDTO serviceAreaUpdateReqDTO);

    List<ServiceAreaResDTO> findAllServiceAreasDTO();
}
