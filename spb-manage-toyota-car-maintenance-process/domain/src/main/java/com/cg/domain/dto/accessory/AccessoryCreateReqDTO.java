package com.cg.domain.dto.accessory;

import com.cg.domain.entity.storage.Accessory;
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
public class AccessoryCreateReqDTO {

    private String name;
    private String code;
    private BigDecimal price;
    private BigDecimal quantity;
    private String unit;
    private Long accessoryRole;

    public Accessory toAccessory(AccessoryRole accessoryRole) {
        return new Accessory()
                .setName(name)
                .setCode(code)
                .setPrice(price)
                .setQuantity(quantity)
                .setUnit(EUnit.getEUnitByName(unit))
                .setAccessoryRole(accessoryRole)
                ;
    }

}
