package com.cg.domain.entity.service.repair;

import com.cg.domain.dto.service.repairItem.RepairItemCreateResDTO;
import com.cg.domain.dto.service.repairItem.RepairItemResDTO;
import com.cg.domain.dto.service.repairItem.RepairItemUpdateResDTO;
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
@Table(name = "repair_items")
public class RepairItem extends BaseEntity {
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

    public RepairItemCreateResDTO toRepairItemCreateResDTO() {
        return new RepairItemCreateResDTO()
                .setId(id)
                .setTitle(title)
                .setServiceArea(serviceArea.toServiceAreaResDTO())
                ;
    }

    public RepairItemUpdateResDTO toRepairItemUpdateResDTO() {
        return new RepairItemUpdateResDTO()
                .setId(id)
                .setTitle(title)
                .setServiceArea(serviceArea.toServiceAreaResDTO())
                ;
    }

    public RepairItemResDTO toRepairItemResDTO() {
        return new RepairItemResDTO()
                .setId(id)
                .setTitle(title)
                .setServiceArea(serviceArea.toServiceAreaResDTO())
                ;
    }

}
