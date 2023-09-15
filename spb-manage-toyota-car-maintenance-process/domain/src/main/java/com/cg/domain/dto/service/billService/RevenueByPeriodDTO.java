package com.cg.domain.dto.service.billService;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RevenueByPeriodDTO {
    private BigDecimal totalAmountByPeriod;
    private List<BillServiceResDTO> billServiceResDTOList;
}
