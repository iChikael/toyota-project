package com.cg.domain.dto.accessory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccessoryUpdateResDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private BigDecimal quantity;
    private String unit;
    private String accessoryRole;
}
