package com.cg.domain.dto.service.orderService.orderMaintenanceItemAccessory;

import com.cg.domain.dto.accessory.AccessoryResDTO;
import com.cg.domain.entity.storage.Accessory;
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
public class OrderMaintenanceItemAccessoryResDTO {
    private Long id;
    private String accessoryName;
    private String accessoryUnit;
    private BigDecimal accessoryQuantity;
    private AccessoryResDTO accessory;

    public OrderMaintenanceItemAccessoryResDTO(Long id, String accessoryName, EUnit accessoryUnit, BigDecimal accessoryQuantity, Accessory accessory) {
        this.id = id;
        this.accessoryName = accessoryName;
        this.accessoryUnit = accessoryUnit.getValue();
        this.accessoryQuantity = accessoryQuantity;
        this.accessory = accessory.toAccessoryResDTO();
    }
}
