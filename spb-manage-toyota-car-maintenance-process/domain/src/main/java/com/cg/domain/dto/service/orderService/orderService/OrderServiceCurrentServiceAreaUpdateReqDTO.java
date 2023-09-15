package com.cg.domain.dto.service.orderService.orderService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderServiceCurrentServiceAreaUpdateReqDTO {
    private String orderServiceId;
    private String serviceAreaId;
}
