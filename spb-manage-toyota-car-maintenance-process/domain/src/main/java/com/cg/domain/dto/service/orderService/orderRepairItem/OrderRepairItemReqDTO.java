package com.cg.domain.dto.service.orderService.orderRepairItem;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderRepairItemReqDTO {
    private String orderServiceId;
    private String serviceAreaId;
}
