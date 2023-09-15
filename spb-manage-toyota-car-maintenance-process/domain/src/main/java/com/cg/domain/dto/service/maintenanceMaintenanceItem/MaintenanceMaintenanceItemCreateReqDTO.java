package com.cg.domain.dto.service.maintenanceMaintenanceItem;

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
public class MaintenanceMaintenanceItemCreateReqDTO implements Validator {

    private String maintenanceItemId;
    private String maintenanceId;
    private String status;

    @Override
    public boolean supports(Class<?> clazz) {
        return MaintenanceMaintenanceItemCreateReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MaintenanceMaintenanceItemCreateReqDTO maintenanceMaintenanceItemCreateReqDTO = (MaintenanceMaintenanceItemCreateReqDTO) target;
    }
}
