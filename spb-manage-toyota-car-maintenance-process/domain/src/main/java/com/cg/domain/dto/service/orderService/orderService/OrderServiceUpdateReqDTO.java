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
public class OrderServiceUpdateReqDTO implements Validator {

    private OrderMaintenanceDTO maintenance;

    private List<OrderRepairDTO> repairs;

    private String timeMinute;
    private String distance;

    @Override
    public boolean supports(Class<?> clazz) {
        return OrderServiceUpdateReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        OrderServiceUpdateReqDTO orderServiceUpdateReqDTO = (OrderServiceUpdateReqDTO) target;
    }
}
