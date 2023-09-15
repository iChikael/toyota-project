package com.cg.domain.entity.storage;

import com.cg.domain.entity.BaseEntity;
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
@Table(name = "delivery_bill")
public class DeliveryBill extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @OneToOne
    private SupplierOrder supplierOrder;

    @OneToMany(mappedBy = "deliveryBill")
    private List<DeliveryBillDetail> deliveryBillDetails;

}
