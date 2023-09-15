package com.cg.domain.dto.service.maintenanceItem;


import com.cg.domain.dto.service.serviceArea.ServiceAreaResDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MaintenanceItemCreateResDTO {
    private Long id;
    private String title;
    private ServiceAreaResDTO serviceArea;
}
