package com.cg.domain.dto.service.repairItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ServiceAreaResDTO {

    private Long id;

    private String name;

    private Long capacity;

    private String status;

}
