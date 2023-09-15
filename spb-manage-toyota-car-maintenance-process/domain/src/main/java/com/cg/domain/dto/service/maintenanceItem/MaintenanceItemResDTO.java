package com.cg.domain.dto.service.maintenanceItem;


import com.cg.domain.dto.service.serviceArea.ServiceAreaResDTO;
import com.cg.domain.entity.service.ServiceArea;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MaintenanceItemResDTO {
    private Long id;
    private String code;
    private String title;
    private ServiceAreaResDTO serviceArea;

    public MaintenanceItemResDTO (Long id, String code, String title, ServiceArea serviceArea) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.serviceArea = serviceArea.toServiceAreaResDTO();
    }
}
