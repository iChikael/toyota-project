package com.cg.domain.dto.service.maintaince;

import com.cg.domain.entity.service.maintenance.Maintenance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MaintenanceCreateReqDTO implements Validator {

    private String title;
    private String unitWage;
    private String priceWage;

    public Maintenance toMaintenance(Long id) {
        return new Maintenance()
                .setId(id)
                .setTitle(title)
                .setUnitWage(unitWage)
                .setPriceWage(BigDecimal.valueOf(Long.parseLong(priceWage)))
                ;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return MaintenanceCreateReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MaintenanceCreateReqDTO maintenanceCreateReqDTO = (MaintenanceCreateReqDTO) target;
    }
}
