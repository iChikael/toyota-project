package com.cg.domain.dto.service.maintenanceItem;


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
public class MaintenanceItemUpdateReqDTO implements Validator {
    private String title;
    private String serviceAreaId;

    @Override
    public boolean supports(Class<?> clazz) {
        return MaintenanceItemUpdateReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MaintenanceItemUpdateReqDTO maintenanceItemUpdateReqDTO = (MaintenanceItemUpdateReqDTO) target;
    }
}
