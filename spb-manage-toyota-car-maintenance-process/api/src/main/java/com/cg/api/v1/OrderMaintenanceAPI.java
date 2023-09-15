package com.cg.api.v1;

import com.cg.domain.dto.service.orderService.orderMaintenance.OrderMaintenanceResDTO;
import com.cg.domain.dto.service.orderService.orderMaintenance.OrderMaintenanceUpdateReqDTO;
import com.cg.domain.entity.orderService.OrderMaintenance;
import com.cg.domain.entity.orderService.OrderService;
import com.cg.exception.DataInputException;
import com.cg.service.order.IOrderMaintenanceService;
import com.cg.service.order.IOrderServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/order-maintenances")
public class OrderMaintenanceAPI {

    @Autowired
    private IOrderMaintenanceService orderMaintenanceService;

    @Autowired
    private IOrderServiceService orderServiceService;

    @GetMapping("/{orderServiceId}")
    public ResponseEntity<?> getOrderMaintenanceByOrderServiceId(@PathVariable String orderServiceId) {
        try {
            Optional<OrderMaintenanceResDTO> orderMaintenanceResDTO = orderMaintenanceService.findOrderMaintenanceResDTOByOrderServiceId(Long.parseLong(orderServiceId));
            return new ResponseEntity<>(orderMaintenanceResDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/update-status")
    public ResponseEntity<?> updateStatus(@RequestBody OrderMaintenanceUpdateReqDTO orderMaintenanceUpdateReqDTO, BindingResult bindingResult) {
        new OrderMaintenanceUpdateReqDTO().validate(orderMaintenanceUpdateReqDTO, bindingResult);

        Optional<OrderService> orderServiceOptional = orderServiceService.findById(Long.parseLong(orderMaintenanceUpdateReqDTO.getOrderServiceId()));
        Optional<OrderMaintenance> orderMaintenanceOptional = orderMaintenanceService.findOrderMaintenanceByOrderService(orderServiceOptional.get());

        if (orderMaintenanceOptional.isEmpty()) {
            throw new DataInputException("Không tìm thấy gói bảo dưỡng");
        }

        OrderMaintenanceResDTO orderMaintenanceResDTO = orderMaintenanceService.updateStatus(orderMaintenanceOptional.get(), orderMaintenanceUpdateReqDTO);

        return new ResponseEntity<>(orderMaintenanceResDTO, HttpStatus.OK);
    }
}
