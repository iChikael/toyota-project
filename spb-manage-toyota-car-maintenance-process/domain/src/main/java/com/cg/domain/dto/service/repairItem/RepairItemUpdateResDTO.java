package com.cg.domain.dto.service.repairItem;

import com.cg.domain.dto.service.serviceArea.ServiceAreaResDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RepairItemUpdateResDTO {
    private Long id;
    private String title;
    private ServiceAreaResDTO serviceArea;
}
