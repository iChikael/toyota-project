package com.cg.service.service.carPlate;

import com.cg.domain.dto.service.carqueue.CarQueueCreateReqDTO;
import com.cg.domain.dto.service.carqueue.CarQueueResDTO;
import com.cg.domain.dto.service.carqueue.CarQueueUpdateReqDTO;
import com.cg.domain.entity.service.CarQueue;
import com.cg.domain.enums.EStatusCarQueue;
import com.cg.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface ICarQueueService extends IGeneralService <CarQueue, Long> {
    List<CarQueueResDTO> findAllCarQueueResDTO();
    Optional<CarQueue> findCarQueueByIdAndDeletedIsFalse (Long id);
    Optional<CarQueue> findCarQueueByCarNumberPlatesAndDeletedIsFalse (String carPlate);
    CarQueue create(CarQueueCreateReqDTO carQueueCreateReqDTO);
    Boolean existsCarQueueByCarNumberPlatesAndStatus(String carPlate, EStatusCarQueue status);
    Boolean existsCarQueueByCarNumberPlates (String carPlate);
    CarQueue update(CarQueue carQueue, CarQueueUpdateReqDTO carQueueUpdateReqDTO);

    Boolean existsCarQueueByCarNumberPlatesAndDeletedIsFalse(String carNumberPlates);
}

