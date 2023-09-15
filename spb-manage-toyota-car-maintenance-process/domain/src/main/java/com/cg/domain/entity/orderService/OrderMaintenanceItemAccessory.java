package com.cg.domain.entity.orderService;

import com.cg.domain.entity.BaseEntity;
import com.cg.domain.entity.bill.BillServiceDetailAccessory;
import com.cg.domain.entity.storage.Accessory;
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
@Table(name = "order_maintenance_item_accessory")
public class OrderMaintenanceItemAccessory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @OneToOne
    private Accessory accessory;

    @ManyToOne
    @JoinColumn(name = "order_maintenance_item_id", referencedColumnName = "id", nullable = false)
    private OrderMaintenanceItem orderMaintenanceItem;

    public BillServiceDetailAccessory toBillServiceDetailAccessory() {
        return new BillServiceDetailAccessory()
                .setAccessoryCode(accessory.getCode())
                .setAccessoryName(accessoryName)
                .setAccessoryUnit(accessoryUnit)
                .setAccessoryQuantity(accessoryQuantity)
                .setAccessoryPrice(accessoryPrice)
                .setAmount(amount);
    }
}
