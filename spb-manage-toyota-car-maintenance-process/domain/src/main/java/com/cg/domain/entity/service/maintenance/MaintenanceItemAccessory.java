package com.cg.domain.entity.service.maintenance;

import com.cg.domain.entity.storage.Accessory;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@Entity
@Table(name = "maintenance_item_accessory", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"maintenance_item_id", "accessory_id"}, name = "UK_maintenance_item_accessory")
})
public class MaintenanceItemAccessory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "accessory_quantity", precision = 10, scale = 2, nullable = false)
    private BigDecimal accessoryQuantity;

    @ManyToOne
    @JoinColumn(name = "maintenance_item_id", referencedColumnName = "id")
    private MaintenanceItem maintenanceItem;

    @ManyToOne
    @JoinColumn(name = "accessory_id", referencedColumnName = "id")
    private Accessory accessory;

}