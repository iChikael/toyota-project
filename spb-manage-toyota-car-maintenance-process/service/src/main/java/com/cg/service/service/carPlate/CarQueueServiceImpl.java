package com.cg.service.service.carPlate;

import com.cg.domain.dto.service.carqueue.CarQueueCreateReqDTO;
import com.cg.domain.dto.service.carqueue.CarQueueResDTO;
import com.cg.domain.dto.service.carqueue.CarQueueUpdateReqDTO;
import com.cg.domain.entity.orderService.OrderService;
import com.cg.domain.entity.service.CarQueue;
import com.cg.domain.enums.EStatusCarQueue;
import com.cg.repository.order.IOrderServiceRepository;
import com.cg.repository.service.carPlate.ICarQueueRepository;
import com.cg.service.order.IOrderServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CarQueueServiceImpl implements ICarQueueService {

    @Autowired
    private ICarQueueRepository carQueueRepository;

    @Autowired
    private IOrderServiceRepository orderServiceRepository;

    @Override
    public List<CarQueue> findAll() {
        return carQueueRepository.findAll();
    }

    @Override
    public Optional<CarQueue> findById(Long id) {
        return carQueueRepository.findById(id);
    }

    @Override
    public CarQueue save(CarQueue carQueue) {
        return carQueueRepository.save(carQueue);
    }

    @Override
    public void delete(CarQueue carQueue) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<CarQueueResDTO> findAllCarQueueResDTO() {
        List<CarQueue> carQueues = carQueueRepository.findAllByDeletedIsFalse();
        List<CarQueueResDTO> carQueueResDTOS = new ArrayList<>();

        for (CarQueue carQueue: carQueues) {
//            Optional<OrderService> orderServiceOptional = orderServiceRepository.findOrderServiceByCarQueueAndDeletedIsFalse(carQueue);
//            if(orderServiceOptional.isEmpty()) {
                carQueueResDTOS.add(carQueue.toCarQueueResDTO());
//            }
        }

        return carQueueResDTOS;
    }

    @Override
    public Optional<CarQueue> findCarQueueByIdAndDeletedIsFalse(Long id) {
        return carQueueRepository.findCarQueueByIdAndDeletedIsFalse(id);
    }

    @Override
    public Optional<CarQueue> findCarQueueByCarNumberPlatesAndDeletedIsFalse(String carPlate) {
        return carQueueRepository.findCarQueueByCarNumberPlatesAndDeletedIsFalse(carPlate);
    }

    @Override
    public CarQueue create(CarQueueCreateReqDTO carQueueCreateReqDTO) {
        CarQueue carQueue = new CarQueue();

        carQueue.setId(null);
        carQueue.setCarNumberPlates(carQueueCreateReqDTO.getCarNumberPlates());
        carQueue.setFullName(carQueueCreateReqDTO.getFullName());
        carQueue.setPhone(carQueueCreateReqDTO.getPhone());
        carQueue.setStatus(EStatusCarQueue.STATUS_WAITING);

        carQueueRepository.save(carQueue);

        return carQueue;
    }

    @Override
    public CarQueue update(CarQueue carQueue, CarQueueUpdateReqDTO carQueueUpdateReqDTO) {

        carQueue.setDeleted(true);

        carQueueRepository.save(carQueue);

        CarQueue updateCarQueue = carQueueUpdateReqDTO.toCarQueue();

        updateCarQueue.setId(null);

        carQueueRepository.save(updateCarQueue);

        return updateCarQueue;
    }

    @Override
    public Boolean existsCarQueueByCarNumberPlatesAndDeletedIsFalse(String carNumberPlates) {
        return carQueueRepository.existsCarQueueByCarNumberPlatesAndDeletedIsFalse(carNumberPlates);
    }

    @Override
    public Boolean existsCarQueueByCarNumberPlatesAndStatus(String carPlate, EStatusCarQueue status) {
        return carQueueRepository.existsCarQueueByCarNumberPlatesAndStatus(carPlate, status);
    }

    @Override
    public Boolean existsCarQueueByCarNumberPlates(String carPlate) {
        return carQueueRepository.existsCarQueueByCarNumberPlates(carPlate);
    }

}
