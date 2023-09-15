package com.cg.api.v1;

import com.cg.domain.dto.service.orderService.orderRepairItemAccessory.OrderRepairItemAccessoryResDTO;
import com.cg.service.order.IOrderRepairItemAccessoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-repair-item-accessories")
public class OrderRepairItemAccessoryAPI {

    @Autowired
    private IOrderRepairItemAccessoryService orderRepairItemAccessoryService;

    @GetMapping("/{orderRepairItemId}")
    public ResponseEntity<List<?>> getAllOrderRepairItemAccessory(@PathVariable String orderRepairItemId) {
        List<OrderRepairItemAccessoryResDTO> orderRepairItemAccessoryResDTOList = orderRepairItemAccessoryService.findAllOrderRepairItemAccessoryResDTOByOrderRepairItemId(Long.parseLong(orderRepairItemId));

        if (orderRepairItemAccessoryResDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(orderRepairItemAccessoryResDTOList, HttpStatus.OK);
    }
}
