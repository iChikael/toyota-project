package com.cg.domain.entity.bill;

import com.cg.domain.dto.service.billService.BillServiceDetailResDTO;
import com.cg.domain.entity.BaseEntity;
import com.cg.domain.enums.EPackService;
import com.cg.domain.enums.EPayment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bill_service_detail")
public class BillServiceDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service_name", nullable = false)
    private String serviceName;

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

    @ManyToOne
    @JoinColumn(name = "bill_service_id", referencedColumnName = "id", nullable = false)
    private BillService billService;

    @OneToMany(mappedBy = "billServiceDetail")
    private List<BillServiceDetailAccessory> billServiceDetailAccessories;

    public BillServiceDetailResDTO toBillServiceDetailResDTO() {
        return new BillServiceDetailResDTO()
                .setId(id)
                .setServiceName(serviceName)
                .setPackName(packName.getValue())
                .setPayment(payment.getValue())
                .setUnitWage(unitWage)
                .setQuantity(quantity)
                .setPriceWage(priceWage)
                .setFees(fees)
                .setDiscount(discount)
                .setAmount(amount);
    }
}
