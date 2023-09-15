package com.cg.domain.dto.service.billService;


import com.cg.domain.entity.bill.BillServiceDetail;
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
public class BillServiceDetailAccessoryResDTO {
    private Long id;
    private String accessoryCode;
    private String accessoryName;
    private String accessoryUnit;
    private BigDecimal accessoryQuantity;
    private BigDecimal accessoryPrice;
    private BigDecimal amount;
    private BillServiceDetailResDTO billServiceDetail;

    public BillServiceDetailAccessoryResDTO(Long id, String accessoryCode, String accessoryName, EUnit accessoryUnit, BigDecimal accessoryQuantity, BigDecimal accessoryPrice, BigDecimal amount, BillServiceDetail billServiceDetail) {
        this.id = id;
        this.accessoryCode = accessoryCode;
        this.accessoryName = accessoryName;
        this.accessoryUnit = accessoryUnit.getValue();
        this.accessoryQuantity = accessoryQuantity;
        this.accessoryPrice = accessoryPrice;
        this.amount = amount;
        this.billServiceDetail = billServiceDetail.toBillServiceDetailResDTO();
    }
}
