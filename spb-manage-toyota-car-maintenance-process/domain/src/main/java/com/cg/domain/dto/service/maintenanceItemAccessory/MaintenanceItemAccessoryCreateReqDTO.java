package com.cg.domain.dto.service.maintenanceItemAccessory;


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
public class MaintenanceItemAccessoryCreateReqDTO implements Validator {

    private String maintenanceItemId;
    private String accessoryId;

    @Override
    public boolean supports(Class<?> clazz) {
        return MaintenanceItemAccessoryCreateReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MaintenanceItemAccessoryCreateReqDTO maintenanceItemAccessoryCreateReqDTO = (MaintenanceItemAccessoryCreateReqDTO) target;
    }

}
