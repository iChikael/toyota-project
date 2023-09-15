package com.cg.domain.entity.bill;

import com.cg.domain.entity.BaseEntity;
import com.cg.domain.enums.EUnit;
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
@Table(name = "bill_service_detail_accessory")
public class BillServiceDetailAccessory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "accessory_code", nullable = false)
    private String accessoryCode;

    @Column(name = "accessory_name", nullable = false)
    private String accessoryName;

    @Enumerated(EnumType.STRING)
    @Column(name = "accessory_unit", nullable = false)
    private EUnit accessoryUnit;

    @Column(name = "accessory_quantity", precision = 10, scale = 2, nullable = false)
    private BigDecimal accessoryQuantity;

    @Column(name = "accessory_price", precision = 10, scale = 0, nullable = false)
    private BigDecimal accessoryPrice;

    @Column(precision = 10, scale = 0, nullable = false)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "bill_service_detail_id", referencedColumnName = "id", nullable = false)
    private BillServiceDetail billServiceDetail;
}
