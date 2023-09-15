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
public class MaintenanceUpdateReqDTO implements Validator {

    private String title;
    private String unitWage;
    private String priceWage;

    @Override
    public boolean supports(Class<?> clazz) {
        return MaintenanceUpdateReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MaintenanceUpdateReqDTO maintenanceUpdateReqDTO = (MaintenanceUpdateReqDTO) target;
    }

    public Maintenance toMaintenance() {
        return new Maintenance()
                .setTitle(title)
                .setUnitWage(unitWage)
                .setPriceWage(BigDecimal.valueOf(Long.parseLong(priceWage)))
                ;
    }
}
