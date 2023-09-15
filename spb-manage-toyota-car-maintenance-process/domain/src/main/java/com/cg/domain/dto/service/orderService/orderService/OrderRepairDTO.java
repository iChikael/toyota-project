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
public class OrderRepairDTO {
    private String repairItemId;
    private String unitWage;
    private String priceWage;
    private String payment;
    private String quantity;
    List<OrderAccessoryDTO> accessories;

}
