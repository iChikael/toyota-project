package com.cg.domain.entity.service.maintenance;

import com.cg.domain.enums.EMaintenanceChecklist;
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
@Table(name = "maintenance_maintenance_item", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"maintenance_id", "maintenance_item_id"}, name = "UK_maintenance_maintenance_item")
})
public class
MaintenanceMaintenanceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private EMaintenanceChecklist name;


    @ManyToOne
    @JoinColumn(name = "maintenance_item_id", referencedColumnName = "id", nullable = false)
    private MaintenanceItem maintenanceItem;

    @ManyToOne
    @JoinColumn(name = "maintenance_id", referencedColumnName = "id", nullable = false)
    private Maintenance maintenance;
}
