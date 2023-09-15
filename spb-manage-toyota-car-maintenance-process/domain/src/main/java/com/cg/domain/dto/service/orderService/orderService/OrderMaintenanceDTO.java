package com.cg.domain.dto.service.orderService.orderService;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderMaintenanceDTO {
    private String maintenanceId;
    private String payment;
    private List<String> items;
    List<OrderAccessoryDTO> accessories;

}
