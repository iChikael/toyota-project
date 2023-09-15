package com.cg.domain.dto.service.orderService.orderService;

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
public class OrderServiceUpdateStatusByServiceAreaReqDTO implements Validator {
    private String orderServiceId;
    private String serviceAreaId;

    @Override
    public boolean supports(Class<?> clazz) {
        return OrderServiceUpdateStatusByServiceAreaReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        OrderServiceUpdateStatusByServiceAreaReqDTO orderServiceUpdateStatusByServiceAreaReqDTO = (OrderServiceUpdateStatusByServiceAreaReqDTO) target;
    }
}
