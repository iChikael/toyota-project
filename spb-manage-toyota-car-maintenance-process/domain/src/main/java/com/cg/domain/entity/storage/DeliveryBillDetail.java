package com.cg.domain.entity.storage;

import com.cg.domain.entity.BaseEntity;
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
@Table(name = "delivery_bill_detail")
public class DeliveryBillDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "accessory_name", nullable = false)
    private String accessoryName;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private BigDecimal price;

    @OneToOne
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "delivery_bill_id", referencedColumnName = "id", nullable = false)
    private DeliveryBill deliveryBill;
}
