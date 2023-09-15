package com.cg.domain.entity.service.maintenance;

import com.cg.domain.dto.service.maintaince.MaintenanceCreateResDTO;
import com.cg.domain.dto.service.maintaince.MaintenanceResDTO;
import com.cg.domain.dto.service.maintaince.MaintenanceUpdateResDTO;
import com.cg.domain.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder(toBuilder = true)
@Table(name = "maintenances")
public class Maintenance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String title;

    @Column(name = "unit_wage", nullable = false)
    private String unitWage;

    @Column(name = "price_wage", precision = 10, scale = 0, nullable = false)
    private BigDecimal priceWage;

    @Column(name = "quantity_wage", precision = 10, scale = 2, nullable = false)
    private BigDecimal quantityWage;

    public MaintenanceResDTO toMaintenanceResDTO() {
        return new MaintenanceResDTO()
                .setId(id)
                .setTitle(title)
                .setUnitWage(unitWage)
                .setPriceWage(priceWage)
                .setQuantityWage(quantityWage)
                ;
    }

    public MaintenanceCreateResDTO toMaintenanceCreateResDTO() {
        return new MaintenanceCreateResDTO()
                .setId(id)
                .setTitle(title)
                .setUnitWage(unitWage)
                .setPriceWage(priceWage)
                ;
    }

    public MaintenanceUpdateResDTO toMaintenanceUpdateResDTO() {
        return new MaintenanceUpdateResDTO()
                .setId(id)
                .setTitle(title)
                .setUnitWage(unitWage)
                .setPriceWage(priceWage)
                ;
    }
}
