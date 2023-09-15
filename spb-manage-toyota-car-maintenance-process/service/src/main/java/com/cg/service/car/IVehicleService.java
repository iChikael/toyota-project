package com.cg.service.car;

import com.cg.domain.dto.car.VehicleResDTO;
import com.cg.domain.entity.car.Vehicle;
import com.cg.service.IGeneralService;

import java.util.List;

public interface IVehicleService extends IGeneralService<Vehicle, Long> {
    List<VehicleResDTO> getAllVehicleResDTO();
}
