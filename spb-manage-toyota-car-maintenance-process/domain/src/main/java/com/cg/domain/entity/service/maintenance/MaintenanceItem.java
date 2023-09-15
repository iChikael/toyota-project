package com.cg.domain.entity.service.maintenance;

import com.cg.domain.dto.service.maintenanceItem.MaintenanceItemCreateResDTO;
import com.cg.domain.dto.service.maintenanceItem.MaintenanceItemResDTO;
import com.cg.domain.dto.service.maintenanceItem.MaintenanceItemUpdateResDTO;
import com.cg.domain.entity.BaseEntity;
import com.cg.domain.entity.service.ServiceArea;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@Entity
@Table(name = "maintenance_items")
public class MaintenanceItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "service_area_id", referencedColumnName = "id", nullable = false)
    private ServiceArea serviceArea;

    public MaintenanceItemResDTO toMaintenanceItemResDTO() {
        return new MaintenanceItemResDTO()
                .setId(id)
                .setTitle(title)
                .setServiceArea(serviceArea.toServiceAreaResDTO());
    }

    public MaintenanceItemCreateResDTO toMaintenanceItemCreateResDTO() {
        return new MaintenanceItemCreateResDTO()
                .setId(id)
                .setTitle(title)
                .setServiceArea(serviceArea.toServiceAreaResDTO());
    }

    public MaintenanceItemUpdateResDTO toMaintenanceItemUpdateResDTO() {
        return new MaintenanceItemUpdateResDTO()
                .setId(id)
                .setTitle(title)
                .setServiceArea(serviceArea.toServiceAreaResDTO());
    }
}
