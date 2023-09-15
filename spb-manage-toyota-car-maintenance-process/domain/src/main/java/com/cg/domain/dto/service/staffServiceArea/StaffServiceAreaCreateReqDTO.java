package com.cg.domain.dto.service.staffServiceArea;


import com.cg.domain.dto.service.managementCarPlateCarQueue.ManagementCarPlateCarQueueCreateReqDTO;
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
public class StaffServiceAreaCreateReqDTO implements Validator {

    private String username;
    private String serviceAreaId;

    @Override
    public boolean supports(Class<?> clazz) {
        return StaffServiceAreaCreateReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        StaffServiceAreaCreateReqDTO staffServiceAreaCreateReqDTO = (StaffServiceAreaCreateReqDTO) target;
    }
}
