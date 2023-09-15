package com.cg.domain.entity.orderService;

import com.cg.domain.dto.service.orderService.orderMaintenanceItem.OrderMaintenanceItemResDTO;
import com.cg.domain.dto.service.orderService.orderRepairItem.OrderRepairItemResDTO;
import com.cg.domain.entity.BaseEntity;
import com.cg.domain.entity.bill.BillServiceDetail;
import com.cg.domain.entity.service.repair.RepairItem;
import com.cg.domain.enums.EPackService;
import com.cg.domain.enums.EPayment;
import com.cg.domain.enums.EStatusOrderServiceDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order_repair_item")
public class OrderRepairItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "repair_item_name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "pack_name", length = 50)
    private EPackService packName;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment", length = 50)
    private EPayment payment;

    @Column(name = "unit_wage", nullable = false)
    private String unitWage;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal quantity;

    @Column(name = "price_wage", precision = 10, scale = 0, nullable = false)
    private BigDecimal priceWage;

    private Long fees;

    @Column(precision = 10, scale = 0)
    private BigDecimal discount;

    @Column(precision = 10, scale = 0, nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_name", length = 50)
    private EStatusOrderServiceDetail status;

    @OneToOne
    private RepairItem repairItem;

    @ManyToOne
    @JoinColumn(name = "order_service_id", referencedColumnName = "id", nullable = false)
    private OrderService orderService;

    public BillServiceDetail toBillServiceDetail() {
        return new BillServiceDetail()
                .setServiceName(name)
                .setPackName(packName)
                .setPayment(payment)
                .setUnitWage(unitWage)
                .setQuantity(quantity)
                .setPriceWage(priceWage)
                .setFees(fees)
                .setDiscount(discount)
                .setAmount(amount);
    }

    public OrderRepairItemResDTO toOrderRepairItemResDTO() {
        return new OrderRepairItemResDTO()
                .setId(id)
                .setName(name)
                .setUnitWage(unitWage)
                .setQuantity(quantity)
                .setStatus(status.getValue())
                .setRepairItem(repairItem.toRepairItemResDTO());
    }
}
