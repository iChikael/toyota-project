package com.cg.domain.dto.accessory;


import com.cg.domain.entity.storage.AccessoryRole;
import com.cg.domain.enums.EUnit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccessoryResDTO {
    private Long id;
    private String name;
    private String code;
    private BigDecimal price;
    private BigDecimal quantity;
    private String unit;
    private String accessoryRole;


    public AccessoryResDTO(Long id, String name, String code, BigDecimal price, BigDecimal quantity, EUnit unit, AccessoryRole accessoryRole) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.price = price;
        this.quantity = quantity;
        this.unit = unit.getValue();
        this.accessoryRole = String.valueOf(accessoryRole.getName());
    }
}
