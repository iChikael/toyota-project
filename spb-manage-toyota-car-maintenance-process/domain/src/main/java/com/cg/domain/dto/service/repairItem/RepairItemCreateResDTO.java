package com.cg.domain.dto.service.repairItem;

import com.cg.domain.dto.service.serviceArea.ServiceAreaResDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RepairItemCreateResDTO {
    private Long id;
    private String title;
    private ServiceAreaResDTO serviceArea;
}
