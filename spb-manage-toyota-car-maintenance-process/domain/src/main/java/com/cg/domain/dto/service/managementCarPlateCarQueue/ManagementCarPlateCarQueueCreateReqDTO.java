package com.cg.domain.dto.service.managementCarPlateCarQueue;


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
public class ManagementCarPlateCarQueueCreateReqDTO implements Validator {
    private String managementCarPlateId;
    private String carQueueId;

    @Override
    public boolean supports(Class<?> clazz) {
        return ManagementCarPlateCarQueueCreateReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ManagementCarPlateCarQueueCreateReqDTO managementCarPlateCarQueueCreateReqDTO = (ManagementCarPlateCarQueueCreateReqDTO) target;
    }
}
