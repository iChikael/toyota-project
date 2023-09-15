package com.cg.domain.entity.bill;

import com.cg.domain.entity.BaseEntity;
import com.cg.domain.entity.car.Car;
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
@Table(name = "bill_car_detail")
public class BillCarDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(precision = 12, scale = 0, nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Long quantity;

    @Column(precision = 12, scale = 0, nullable = false)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "bill_car_id", referencedColumnName = "id", nullable = false)
    private BillCar billCar;

    @OneToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id", nullable = false)
    private Car car;

}