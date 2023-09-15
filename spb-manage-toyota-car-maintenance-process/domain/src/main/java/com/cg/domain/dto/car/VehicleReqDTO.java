package com.cg.domain.dto.car;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VehicleReqDTO {
    private Long id;
    private String name;
    private String modelCode;
}
