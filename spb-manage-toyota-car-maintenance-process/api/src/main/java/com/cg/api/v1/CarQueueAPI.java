package com.cg.api.v1;

import com.cg.domain.dto.service.carqueue.CarQueueCreateReqDTO;
import com.cg.domain.dto.service.carqueue.CarQueueResDTO;
import com.cg.domain.dto.service.carqueue.CarQueueUpdateReqDTO;
import com.cg.domain.entity.service.CarQueue;
import com.cg.domain.enums.EStatusCarQueue;
import com.cg.exception.DataInputException;
import com.cg.service.service.carPlate.ICarQueueService;
import com.cg.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/car-queues")
public class CarQueueAPI {

    @Autowired
    private ICarQueueService carQueueService;

    @Autowired
    private ValidateUtils validateUtils;

    @GetMapping
    public ResponseEntity<List<?>> getAllCarQueue() {

        List<CarQueueResDTO> carQueueResDTOS = carQueueService.findAllCarQueueResDTO();

        if (carQueueResDTOS.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(carQueueResDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {

        Optional<CarQueue> carQueueOptional = carQueueService.findCarQueueByIdAndDeletedIsFalse(id);

        if (carQueueOptional.isEmpty()) {
            throw new DataInputException("Xe chờ không tồn tại !");
        }

        CarQueue carQueue = carQueueOptional.get();
        CarQueueResDTO carQueueResDTO = carQueue.toCarQueueResDTO();

        return new ResponseEntity<>(carQueueResDTO, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCarQueue(@RequestBody CarQueueCreateReqDTO carQueueCreateReqDTO, BindingResult bindingResult) {
        new CarQueueCreateReqDTO().validate(carQueueCreateReqDTO, bindingResult);
        CarQueue carQueue;

        if (carQueueService.existsCarQueueByCarNumberPlatesAndDeletedIsFalse(carQueueCreateReqDTO.getCarNumberPlates())) {
            throw new DataInputException("Thông tin xe đã tồn tại!");
        }

        carQueue = carQueueService.create(carQueueCreateReqDTO);

        return new ResponseEntity<>(carQueue.toCarQueueCreateResDTO(), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCarQueue(@PathVariable String id, @RequestBody CarQueueUpdateReqDTO carQueueUpdateReqDTO, BindingResult bindingResult) {
        new CarQueueUpdateReqDTO().validate(carQueueUpdateReqDTO, bindingResult);

        if (!validateUtils.isNumberValid(id)) {
            throw new DataInputException("Mã xe không hợp lệ");
        }

        Long carQueueId = Long.parseLong(id);
        Optional<CarQueue> carQueueOptional = carQueueService.findCarQueueByIdAndDeletedIsFalse(carQueueId);

        if (carQueueOptional.isPresent()) {

            CarQueue carQueue;

            if (EStatusCarQueue.getEStatusCarQueueByName(carQueueUpdateReqDTO.getStatus()) == EStatusCarQueue.STATUS_WAITING) {

                carQueue = carQueueService.update(carQueueOptional.get(), carQueueUpdateReqDTO);

                return new ResponseEntity<>(carQueue.toCarQueueUpdateResDTO(), HttpStatus.OK);

            } else {
                String carQueueStatus = carQueueUpdateReqDTO.getStatus();

                if (carQueueOptional.get().getStatus() == EStatusCarQueue.STATUS_WAITING
                        && EStatusCarQueue.getEStatusCarQueueByName(carQueueStatus) == EStatusCarQueue.STATUS_DOING
                        || carQueueOptional.get().getStatus() == EStatusCarQueue.STATUS_DOING
                        && EStatusCarQueue.getEStatusCarQueueByName(carQueueStatus) == EStatusCarQueue.STATUS_DONE
                ) {

                    CarQueue carQueueUpdate = carQueueOptional.get();

                    carQueueUpdate.setStatus(EStatusCarQueue.getEStatusCarQueueByName(carQueueUpdateReqDTO.getStatus()));

                    carQueueService.save(carQueueUpdate);

                    return new ResponseEntity<>(carQueueUpdate.toCarQueueUpdateResDTO(), HttpStatus.OK);
                } else {
                    throw new DataInputException("Không thể sửa thông tin!");
                }
            }
        } else {
            throw new DataInputException("Không tìm thấy thông tin xe!");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<CarQueue> carQueueOptional = carQueueService.findCarQueueByIdAndDeletedIsFalse(id);

        if (carQueueOptional.isEmpty()) {
            throw new DataInputException("Xe chờ không tồn tại !");
        }

        CarQueue carQueue = carQueueOptional.get();
        carQueue.setDeleted(true);

        carQueueService.save(carQueue);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
