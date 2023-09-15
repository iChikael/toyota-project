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
public class RevenueByDayDTO {
    private Long day;
    private BigDecimal totalAmountByDay;
    private List<BillServiceResDTO> billServiceResDTOList;
}
