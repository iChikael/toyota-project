package com.cg.domain.dto.service.billService;


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
public class BillReqDTO implements Validator {
    private String carPlate;

    @Override
    public boolean supports(Class<?> clazz) {
        return BillReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BillReqDTO billReqDTO = (BillReqDTO) target;
    }
}
