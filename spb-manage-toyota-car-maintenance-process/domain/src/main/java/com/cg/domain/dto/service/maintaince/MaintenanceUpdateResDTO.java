package com.cg.domain.dto.service.maintaince;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MaintenanceUpdateResDTO {
    private Long id;
    private String title;
    private String unitWage;
    private BigDecimal priceWage;
}
