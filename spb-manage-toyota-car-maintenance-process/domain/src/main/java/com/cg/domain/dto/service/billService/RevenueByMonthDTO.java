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
public class RevenueByMonthDTO {
    private Long month;
    private BigDecimal totalAmountByMonth;
    private List<RevenueByDayDTO> revenueByDayDTOS;
    private List<BillServiceResDTO> billServiceResDTOList;
}
