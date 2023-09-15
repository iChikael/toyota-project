package com.cg.domain.dto.service.orderService.orderMaintenance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderMaintenanceUpdateReqDTO implements Validator {
    private String orderServiceId;
    private String status;
    @Override
    public boolean supports(Class<?> clazz) {
        return OrderMaintenanceUpdateReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        OrderMaintenanceUpdateReqDTO orderMaintenanceUpdateReqDTO = (OrderMaintenanceUpdateReqDTO) target;
    }
}
