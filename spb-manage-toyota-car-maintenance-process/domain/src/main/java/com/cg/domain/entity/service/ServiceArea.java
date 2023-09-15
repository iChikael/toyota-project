package com.cg.domain.entity.service;

import com.cg.domain.dto.service.serviceArea.ServiceAreaCreateResDTO;
import com.cg.domain.dto.service.serviceArea.ServiceAreaUpdateResDTO;
import com.cg.domain.dto.service.serviceArea.ServiceAreaResDTO;
import com.cg.domain.entity.BaseEntity;
import com.cg.domain.entity.service.repair.RepairItem;
import com.cg.domain.enums.EStatusServiceArea;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "service_area")
public class ServiceArea extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long capacity;

    @Column(name="current_capacity", nullable = false, columnDefinition = "BIGINT default 0")
    private Long currentCapacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_name", length = 50)
    private EStatusServiceArea status;

    @OneToMany(mappedBy = "serviceArea")
    private List<RepairItem> repairItems;


    public ServiceAreaResDTO toServiceAreaResDTO() {
        return new ServiceAreaResDTO()
                .setId(id)
                .setName(name)
                .setCapacity(capacity)
                .setCurrentCapacity(currentCapacity)
                .setStatus(status.getValue())
                ;
    }

    public ServiceAreaCreateResDTO toServiceAreaCreateResDTO() {
        return new ServiceAreaCreateResDTO()
                .setId(id)
                .setName(name)
                .setCapacity(capacity)
                .setCurrentCapacity(currentCapacity)
                .setStatus(status.getValue())
                ;
    }

    public ServiceAreaUpdateResDTO toServiceAreaUpdateResDTO() {
        return new ServiceAreaUpdateResDTO()
                .setName(name)
                .setCapacity(capacity)
                .setCurrentCapacity(currentCapacity)
                .setStatus(status.getValue())
                ;
    }

}
