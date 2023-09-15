package com.cg.domain.dto.service.repairItem;

import com.cg.domain.entity.service.repair.RepairItem;
import com.cg.domain.entity.service.ServiceArea;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RepairItemUpdateReqDTO {

    private String title;
    private String serviceArea;


    public RepairItem toRepairItem(ServiceArea serviceArea){
        return new RepairItem()
                .setTitle(title)
                .setServiceArea(serviceArea)
                ;
    }


}
