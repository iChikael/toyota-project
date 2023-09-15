package com.cg.domain.dto.service.repairItemAccessory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RepairItemAccessoryUpdateReqDTO {
    private String repairItem;

    private String accessory;
}
