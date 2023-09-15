package com.cg.domain.dto.service.orderService.orderMaintenanceItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderMaintenanceItemUpdateStatusReqDTO {
    private String orderServiceId;
    private String serviceAreaId;
    private String maintenanceItemId;
    private String status;
}
