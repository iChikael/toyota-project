package com.cg.domain.entity.bill;

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
@Table(name = "bill_car")
public class BillCar extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_amount", precision = 12, scale = 0, nullable = false)
    private BigDecimal totalAmount;

    @OneToMany(mappedBy = "billCar")
    private List<BillCarDetail> billCarDetails;
}
