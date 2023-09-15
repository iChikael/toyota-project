package com.cg.api.v1;

import com.cg.domain.dto.service.orderService.orderMaintenanceItemAccessory.OrderMaintenanceItemAccessoryResDTO;
import com.cg.service.order.IOrderMaintenanceItemAccessoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-maintenance-item-accessories")
public class OrderMaintenanceItemAccessoryAPI {

    @Autowired
    private IOrderMaintenanceItemAccessoryService orderMaintenanceItemAccessoryService;
    @GetMapping("/{orderMaintenanceItemId}")
    public ResponseEntity<List<?>> getOrderMaintenanceItemAccessoryByOrderMaintenanceItemId(@PathVariable String orderMaintenanceItemId) {
        try {
            List<OrderMaintenanceItemAccessoryResDTO> orderMaintenanceItemAccessoryResDTOList = orderMaintenanceItemAccessoryService.findAllOrderMaintenanceItemAccessoryResDTOByOrderMaintenanceItemId(Long.parseLong(orderMaintenanceItemId));
            if (orderMaintenanceItemAccessoryResDTOList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orderMaintenanceItemAccessoryResDTOList, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
