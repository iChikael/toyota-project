package com.cg.api.v1;

import com.cg.domain.dto.service.orderService.orderService.IOrderServiceCurrentServiceAreaResDTO;
import com.cg.domain.dto.service.orderService.orderService.OrderServiceCurrentServiceAreaUpdateReqDTO;
import com.cg.domain.entity.orderService.OrderServiceCurrentServiceArea;
import com.cg.exception.DataInputException;
import com.cg.service.order.IOrderServiceCurrentServiceAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/order-service-current-service-areas")
public class OrderServiceCurrentServiceAreaAPI {

    @Autowired
    private IOrderServiceCurrentServiceAreaService orderServiceCurrentServiceAreaService;

    @GetMapping("/time-create/{orderServiceId}")
    public ResponseEntity<?> getOrderServiceTimeCreate(@PathVariable String orderServiceId) {
        List<IOrderServiceCurrentServiceAreaResDTO> orderServiceCurrentServiceAreaResDTOList = orderServiceCurrentServiceAreaService.findByOrderServiceIdAndTimeCreate(Long.parseLong(orderServiceId));

        if (orderServiceCurrentServiceAreaResDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(orderServiceCurrentServiceAreaResDTOList, HttpStatus.OK);
    }

    @GetMapping("/time-done/{orderServiceId}")
    public ResponseEntity<?> getOrderServiceTimeDone(@PathVariable String orderServiceId) {
        Optional<IOrderServiceCurrentServiceAreaResDTO> orderServiceCurrentServiceAreaResDTOOptional = orderServiceCurrentServiceAreaService.findByOrderServiceIdAndTimeDone(Long.parseLong(orderServiceId));

        if (orderServiceCurrentServiceAreaResDTOOptional.isEmpty()) {
            throw new DataInputException("Mã dịch vụ không tồn tại");
        }

        return new ResponseEntity<>(orderServiceCurrentServiceAreaResDTOOptional.get(), HttpStatus.OK);
    }

    @PatchMapping("/update-service-area")
    public ResponseEntity<?> updateCurrentServiceArea(@RequestBody OrderServiceCurrentServiceAreaUpdateReqDTO orderServiceCurrentServiceAreaUpdateReqDTO) {
        OrderServiceCurrentServiceArea orderServiceCurrentServiceArea = orderServiceCurrentServiceAreaService.updateServiceAreaName(orderServiceCurrentServiceAreaUpdateReqDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
