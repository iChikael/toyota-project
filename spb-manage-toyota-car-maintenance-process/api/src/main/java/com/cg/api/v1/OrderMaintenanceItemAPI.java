package com.cg.api.v1;

import com.cg.domain.dto.service.orderService.orderMaintenanceItem.OrderMaintenanceItemReqDTO;
import com.cg.domain.dto.service.orderService.orderMaintenanceItem.OrderMaintenanceItemResDTO;
import com.cg.domain.dto.service.orderService.orderMaintenanceItem.OrderMaintenanceItemUpdateReqDTO;
import com.cg.domain.dto.service.orderService.orderMaintenanceItem.OrderMaintenanceItemUpdateStatusReqDTO;
import com.cg.domain.entity.orderService.OrderMaintenance;
import com.cg.domain.entity.orderService.OrderService;
import com.cg.service.order.IOrderMaintenanceItemService;
import com.cg.service.order.IOrderMaintenanceService;
import com.cg.service.order.IOrderServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/order-maintenance-items")
public class OrderMaintenanceItemAPI {

    @Autowired
    private IOrderMaintenanceItemService orderMaintenanceItemService;

    @Autowired
    private IOrderMaintenanceService orderMaintenanceService;

    @Autowired
    private IOrderServiceService orderServiceService;

    @GetMapping("/{orderServiceId}")
    public ResponseEntity<List<?>> getOrderMaintenanceByOrderMaintenanceId(@PathVariable String orderServiceId) {
        Optional<OrderService> orderServiceOptional = orderServiceService.findById(Long.parseLong(orderServiceId));
        Optional<OrderMaintenance> orderMaintenanceOptional = orderMaintenanceService.findOrderMaintenanceByOrderService(orderServiceOptional.get());

        List<OrderMaintenanceItemResDTO> orderMaintenanceItemResDTOList = orderMaintenanceItemService.findAllOrderMaintenanceItemResDTOByOrderMaintenanceId(orderMaintenanceOptional.get().getId());

        if (orderMaintenanceItemResDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(orderMaintenanceItemResDTOList, HttpStatus.OK);
    }

    @PostMapping("/service-area")
    public ResponseEntity<List<?>> getAllMaintenanceItemByServiceArea(@RequestBody OrderMaintenanceItemReqDTO orderMaintenanceItemReqDTO) {
        List<OrderMaintenanceItemResDTO> orderMaintenanceItemResDTOList = orderMaintenanceItemService.findAllOrderMaintenanceItemByServiceAreaIdResDTO(orderMaintenanceItemReqDTO);

        if (orderMaintenanceItemResDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(orderMaintenanceItemResDTOList, HttpStatus.OK);
    }

    @PatchMapping("/update-status")
    public ResponseEntity<?> updateStatus(@RequestBody OrderMaintenanceItemUpdateReqDTO orderMaintenanceItemUpdateReqDTO) {
        List<OrderMaintenanceItemResDTO> orderMaintenanceItemResDTOList = orderMaintenanceItemService.updateStatus(orderMaintenanceItemUpdateReqDTO);

        if (orderMaintenanceItemResDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(orderMaintenanceItemResDTOList, HttpStatus.OK);
    }

    @PatchMapping("/update-status-done")
    public ResponseEntity<?> updateStatus(@RequestBody OrderMaintenanceItemUpdateStatusReqDTO orderMaintenanceItemUpdateStatusReqDTO) {
        List<OrderMaintenanceItemResDTO> orderMaintenanceItemResDTOList = orderMaintenanceItemService.updateStatusDone(orderMaintenanceItemUpdateStatusReqDTO);

        if (orderMaintenanceItemResDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(orderMaintenanceItemResDTOList, HttpStatus.OK);
    }

    @PatchMapping("/update-status-done-all")
    public ResponseEntity<?> updateStatusAll(@RequestBody OrderMaintenanceItemUpdateStatusReqDTO orderMaintenanceItemUpdateStatusReqDTO) {
        List<OrderMaintenanceItemResDTO> orderMaintenanceItemResDTOList = orderMaintenanceItemService.updateStatusDoneAll(orderMaintenanceItemUpdateStatusReqDTO);

        if (orderMaintenanceItemResDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(orderMaintenanceItemResDTOList, HttpStatus.OK);
    }
}
