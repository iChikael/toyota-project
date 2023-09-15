package com.cg.domain.dto.service.repairItem;

import com.cg.domain.dto.service.serviceArea.ServiceAreaResDTO;
import com.cg.domain.entity.service.ServiceArea;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RepairItemResDTO {

    private Long id;
    private String code;
    private String title;
    private ServiceAreaResDTO serviceArea;


    public RepairItemResDTO (Long id, String code, String title, ServiceArea serviceArea) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.serviceArea = serviceArea.toServiceAreaResDTO();
    }
}
