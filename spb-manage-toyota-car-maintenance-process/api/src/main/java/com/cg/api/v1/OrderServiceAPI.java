package com.cg.api.v1;

import com.cg.domain.dto.service.billService.BillReqDTO;
import com.cg.domain.dto.service.orderService.orderService.*;
import com.cg.domain.entity.bill.BillService;
import com.cg.domain.entity.orderService.OrderService;
import com.cg.domain.entity.service.CarQueue;
import com.cg.domain.entity.service.ServiceArea;
import com.cg.domain.enums.EStatusCarQueue;
import com.cg.domain.enums.EStatusOrderService;
import com.cg.exception.DataInputException;
import com.cg.service.bill.IBillServiceService;
import com.cg.service.order.*;
import com.cg.service.service.IServiceAreaService;
import com.cg.service.service.carPlate.ICarQueueService;
import com.cg.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/order-services")
public class OrderServiceAPI {

    @Autowired
    private IOrderServiceService orderServiceService;

    @Autowired
    private IBillServiceService billServiceService;

    @Autowired
    private ICarQueueService carQueueService;

    @Autowired
    private ValidateUtils validateUtils;

    @Autowired
    private IServiceAreaService serviceAreaService;

    @GetMapping
    public ResponseEntity<List<?>> getAllOrderServices() {
        List<OrderServiceResDTO> orderServiceResDTOList = orderServiceService.findAllOrderServiceByStatusResDTO(EStatusOrderService.STATUS_WAITING_PAYMENT);

        if (orderServiceResDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(orderServiceResDTOList, HttpStatus.OK);
    }

    @GetMapping("/done")
    public ResponseEntity<List<?>> getAllOrderServicesDone() {
        List<OrderServiceResDTO> orderServiceResDTOList = orderServiceService.findAllByStatusCarQueueDoneAndDeletedIsFalse(EStatusCarQueue.STATUS_DONE);

        if (orderServiceResDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(orderServiceResDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAllOrderServiceById(@PathVariable String id) {
        OrderServiceResDTO orderServiceResDTO = orderServiceService.getOrderServiceResDTOByIdAndStatus(Long.parseLong(id), EStatusOrderService.STATUS_WAITING_PAYMENT);

        return new ResponseEntity<>(orderServiceResDTO, HttpStatus.OK);
    }

    @GetMapping("/service-area/{serviceAreaId}")
    public ResponseEntity<List<?>> getAllOrderServiceByServiceArea(@PathVariable String serviceAreaId) {
        Optional<ServiceArea> serviceAreaOptional = serviceAreaService.findByIdAndDeletedIsFalse(Long.parseLong(serviceAreaId));

        if (serviceAreaOptional.isEmpty()) {
            throw new DataInputException("Mã khu vực không tồn tại");
        }

        List<OrderServiceResDTO> orderServiceResDTOList = orderServiceService.findAllOrderServiceByServiceAreaNameAndDeletedIsFalseResDTO(serviceAreaOptional.get().getName());

        return new ResponseEntity<>(orderServiceResDTOList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createOrderService(@RequestBody OrderServiceCreateReqDTO orderServiceCreateReqDTO, BindingResult bindingResult) throws ParseException {
        new OrderServiceCreateReqDTO().validate(orderServiceCreateReqDTO, bindingResult);

        if (!validateUtils.isNumberValid(orderServiceCreateReqDTO.getCarQueueId())) {
            throw new DataInputException("Mã xe chờ không hợp lệ");
        }

        Optional<CarQueue> carQueueOptional = carQueueService.findCarQueueByIdAndDeletedIsFalse(Long.parseLong(orderServiceCreateReqDTO.getCarQueueId()));

        if (carQueueOptional.isEmpty()) {
            throw new DataInputException("Không tìm thấy xe hàng chờ!");
        }

        CarQueue carQueue = carQueueOptional.get();
        OrderService orderService = orderServiceService.createOrderService(orderServiceCreateReqDTO, carQueue);

        return new ResponseEntity<>(orderService, HttpStatus.OK);
    }

    @PostMapping("/payment")
    public ResponseEntity<?> paymentOrderService(@RequestBody BillReqDTO billReqDTO, BindingResult bindingResult) {
        new BillReqDTO().validate(billReqDTO, bindingResult);

        Optional<CarQueue> carQueueOptional = carQueueService.findCarQueueByCarNumberPlatesAndDeletedIsFalse(billReqDTO.getCarPlate());

        if (carQueueOptional.isEmpty()) {
            throw new DataInputException("Không tìm thấy xe sử dụng dịch vụ");
        }

        CarQueue carQueue = carQueueOptional.get();
        Optional<OrderService> orderServiceOptional = orderServiceService.findByCarQueueAndDeletedIsFalse(carQueue);

        if (orderServiceOptional.isEmpty()) {
            throw new DataInputException("Xe chưa chọn dịch vụ!");
        }

        BillService billService = billServiceService.create(orderServiceOptional.get(), carQueue);

        return new ResponseEntity<>(billService, HttpStatus.OK);
    }

    @PatchMapping("/update-status")
    public ResponseEntity<?> updateStatusCarQueue(@RequestBody OrderServiceUpdateStatusCarQueueReqDTO orderServiceUpdateStatusCarQueueReqDTO) {
        try {
            orderServiceService.updateStatusCarQueue(orderServiceUpdateStatusCarQueueReqDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateOrderService(@PathVariable String id, @RequestBody OrderServiceUpdateReqDTO orderServiceUpdateReqDTO, BindingResult bindingResult) {
        new OrderServiceUpdateReqDTO().validate(orderServiceUpdateReqDTO, bindingResult);

        if (!validateUtils.isNumberValid(id)) {
            throw new DataInputException("Mã order không hợp lệ");
        }

        Long orderServiceId = Long.parseLong(id);
        Optional<OrderService> orderServiceOptional = orderServiceService.findByIdAndDeletedIsFalse(orderServiceId);

        if (orderServiceOptional.isPresent()) {
            OrderService orderService = orderServiceService.update(orderServiceOptional.get(), orderServiceUpdateReqDTO);

            return new ResponseEntity<>(orderService, HttpStatus.OK);
        } else {
            throw new DataInputException("Không tìm thấy thông tin order!");
        }
    }
}
