package com.cg.domain.dto.service.orderService.orderMaintenanceItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderMaintenanceItemUpdateReqDTO {
    private String orderMaintenanceId;
    private String serviceAreaId;
    private String status;
}
