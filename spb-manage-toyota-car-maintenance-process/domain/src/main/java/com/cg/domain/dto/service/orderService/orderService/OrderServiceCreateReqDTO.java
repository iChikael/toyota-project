package com.cg.domain.dto.service.orderService.orderService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderServiceCreateReqDTO implements Validator {
    private String carQueueId;
    private OrderMaintenanceDTO maintenance;
    private List<OrderRepairDTO> repairs;
    private String distance;
    private String customerReq;
    private String doEarly;
    @Override
    public boolean supports(Class<?> clazz) {
        return OrderServiceCreateReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        OrderServiceCreateReqDTO orderServiceCreateReqDTO = (OrderServiceCreateReqDTO) target;
    }
}
