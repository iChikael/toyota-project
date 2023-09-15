package com.cg.api.v1;


import com.cg.domain.dto.service.managementCarPlateCarQueue.ManagementCarPlateCarQueueCreateReqDTO;
import com.cg.domain.entity.service.CarQueue;
import com.cg.domain.entity.service.ManagementCarPlate;
import com.cg.domain.entity.service.ManagementCarPlateCarQueue;
import com.cg.exception.DataInputException;
import com.cg.service.service.carPlate.ICarQueueService;
import com.cg.service.service.carPlate.IManagementCarPlateCarQueueService;
import com.cg.service.service.carPlate.IManagementCarPlateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/car-plate-car-queues")
public class ManagementCarPlateCarQueueAPI {

    @Autowired
    private IManagementCarPlateCarQueueService managementCarPlateCarQueueService;

    @Autowired
    private IManagementCarPlateService managementCarPlateService;

    @Autowired
    private ICarQueueService carQueueService;

    @PostMapping
    public ResponseEntity<?> createManagementCarPlateCarQueue(@RequestBody ManagementCarPlateCarQueueCreateReqDTO managementCarPlateCarQueueCreateReqDTO, BindingResult bindingResult) {

        new ManagementCarPlateCarQueueCreateReqDTO().validate(managementCarPlateCarQueueCreateReqDTO, bindingResult);

        Optional<ManagementCarPlate> managementCarPlateOptional = managementCarPlateService.findManagementCarPlateByIdAndDeletedIsFalse(Long.parseLong(managementCarPlateCarQueueCreateReqDTO.getManagementCarPlateId()));

        if (managementCarPlateOptional.isEmpty()) {
            throw new DataInputException("Không tìm thấy thông tin quản lý biển số xe!");
        }

        Optional<CarQueue> carQueueOptional = carQueueService.findCarQueueByIdAndDeletedIsFalse(Long.parseLong(managementCarPlateCarQueueCreateReqDTO.getCarQueueId()));

        if (carQueueOptional.isEmpty()) {
            throw new DataInputException("Không tìm thấy thông tin xe!");
        }

        ManagementCarPlateCarQueue managementCarPlateCarQueue = new ManagementCarPlateCarQueue();
        managementCarPlateCarQueue.setId(null);
        managementCarPlateCarQueue.setManagementCarPlate(managementCarPlateOptional.get());
        managementCarPlateCarQueue.setCarQueue(carQueueOptional.get());

        managementCarPlateCarQueueService.save(managementCarPlateCarQueue);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
