package com.cg.domain.dto.service.orderService.orderMaintenance;

import com.cg.domain.dto.service.maintaince.MaintenanceResDTO;
import com.cg.domain.dto.service.orderService.orderMaintenanceItem.OrderMaintenanceItemResDTO;
import com.cg.domain.entity.service.maintenance.Maintenance;
import com.cg.domain.enums.EPackService;
import com.cg.domain.enums.EPayment;
import com.cg.domain.enums.EStatusOrderServiceDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderMaintenanceResDTO {
    private Long id;
    private String name;
    private String packName;
    private String payment;
    private String unitWage;
    private BigDecimal quantity;
    private BigDecimal priceWage;
    private Long fees;
    private BigDecimal discount;
    private BigDecimal amount;
    private String status;
    private MaintenanceResDTO maintenance;

    public OrderMaintenanceResDTO(Long id, String name, EPackService packName, EPayment payment, String unitWage, BigDecimal quantity, BigDecimal priceWage, Long fees, BigDecimal discount, BigDecimal amount, EStatusOrderServiceDetail status, Maintenance maintenance) {
        this.id = id;
        this.name = name;
        this.packName = packName.getValue();
        this.payment = payment.getValue();
        this.unitWage = unitWage;
        this.quantity = quantity;
        this.priceWage = priceWage;
        this.fees = fees;
        this.discount = discount;
        this.amount = amount;
        this.status = status.getValue();
        this.maintenance = maintenance.toMaintenanceResDTO();
    }
}
