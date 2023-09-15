package com.cg.api.v1;

import com.cg.domain.dto.service.orderService.orderRepairItem.OrderRepairItemReqDTO;
import com.cg.domain.dto.service.orderService.orderRepairItem.OrderRepairItemResDTO;
import com.cg.domain.dto.service.orderService.orderRepairItem.OrderRepairItemUpdateReqDTO;
import com.cg.domain.dto.service.orderService.orderRepairItem.OrderRepairItemUpdateStatusReqDTO;
import com.cg.service.order.IOrderRepairItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-repair-items")
public class OrderRepairItemAPI {

    @Autowired
    private IOrderRepairItemService orderRepairItemService;

    @GetMapping("/{orderServiceId}")
    public ResponseEntity<List<?>> getOrderMaintenanceByOrderServiceId(@PathVariable String orderServiceId) {
        List<OrderRepairItemResDTO> orderRepairItemResDTOList = orderRepairItemService.findAllOrderRepairItemResDTOByOrderServiceId(Long.parseLong(orderServiceId));

        if (orderRepairItemResDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(orderRepairItemResDTOList, HttpStatus.OK);
    }

    @PostMapping("/service-area")
    public ResponseEntity<List<?>> getAllRepairItemByServiceArea(@RequestBody OrderRepairItemReqDTO orderRepairItemReqDTO) {
        List<OrderRepairItemResDTO> orderRepairItemResDTOList = orderRepairItemService.findAllOrderRepairItemByServiceAreaIdResDTO(orderRepairItemReqDTO);

        if (orderRepairItemResDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(orderRepairItemResDTOList,HttpStatus.OK);
    }
    @PatchMapping("/update-status")
    public ResponseEntity<?> updateStatus(@RequestBody OrderRepairItemUpdateReqDTO orderRepairItemUpdateReqDTO) {
        List<OrderRepairItemResDTO> orderRepairItemResDTOList = orderRepairItemService.updateStatus(orderRepairItemUpdateReqDTO);

        if (orderRepairItemResDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(orderRepairItemResDTOList, HttpStatus.OK);
    }
    @PatchMapping("/update-status-done")
    public ResponseEntity<?> updateStatus(@RequestBody OrderRepairItemUpdateStatusReqDTO orderRepairItemUpdateStatusReqDTO) {
        List<OrderRepairItemResDTO> orderRepairItemResDTOList = orderRepairItemService.updateStatusDone(orderRepairItemUpdateStatusReqDTO);

        if (orderRepairItemResDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(orderRepairItemResDTOList, HttpStatus.OK);
    }

    @PatchMapping("/update-status-done-all")
    public ResponseEntity<?> updateStatusAll(@RequestBody OrderRepairItemUpdateStatusReqDTO orderRepairItemUpdateStatusReqDTO) {
        List<OrderRepairItemResDTO> orderRepairItemResDTOList = orderRepairItemService.updateStatusDoneAll(orderRepairItemUpdateStatusReqDTO);

        if (orderRepairItemResDTOList == null || orderRepairItemResDTOList.isEmpty() ) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(orderRepairItemResDTOList, HttpStatus.OK);
    }
}
