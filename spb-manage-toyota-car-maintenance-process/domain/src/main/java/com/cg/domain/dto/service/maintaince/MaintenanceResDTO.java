package com.cg.domain.dto.service.maintaince;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
public class MaintenanceResDTO {

    private Long id;
    private String code;
    private String title;
    private String unitWage;
    private BigDecimal priceWage;
    private BigDecimal quantityWage;

    public MaintenanceResDTO(Long id, String code, String title, String unitWage, BigDecimal priceWage, BigDecimal quantityWage) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.unitWage = unitWage;
        this.priceWage = priceWage;
        this.quantityWage = quantityWage;
    }
}
