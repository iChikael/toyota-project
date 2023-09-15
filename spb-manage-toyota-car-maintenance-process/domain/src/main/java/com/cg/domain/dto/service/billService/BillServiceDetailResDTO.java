package com.cg.domain.dto.service.billService;

import com.cg.domain.entity.bill.BillService;
import com.cg.domain.enums.EPackService;
import com.cg.domain.enums.EPayment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BillServiceDetailResDTO {
    private Long id;
    private String serviceName;
    private String packName;
    private String payment;
    private String unitWage;
    private BigDecimal quantity;
    private BigDecimal priceWage;
    private Long fees;
    private BigDecimal discount;
    private BigDecimal amount;
    private BillServiceResDTO billService;

    public BillServiceDetailResDTO(Long id, String serviceName, EPackService packName, EPayment payment, String unitWage, BigDecimal quantity, BigDecimal priceWage, Long fees, BigDecimal discount, BigDecimal amount, BillService billService) {
        this.id = id;
        this.serviceName = serviceName;
        this.packName = packName.getValue();
        this.payment = payment.getValue();
        this.unitWage = unitWage;
        this.quantity = quantity;
        this.priceWage = priceWage;
        this.fees = fees;
        this.discount = discount;
        this.amount = amount;
        this.billService = billService.toBillServiceResDTO();
    }
}
