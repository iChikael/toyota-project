package com.cg.domain.entity.orderService;

import com.cg.domain.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order_service_current_service_areas")
public class OrderServiceCurrentServiceArea extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_service_id", referencedColumnName = "id")
    private OrderService orderService;

    @Column(name = "service_area_name")
    private String serviceAreaName;
}
