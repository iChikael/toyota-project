package com.cg.domain.dto.car;

import com.cg.domain.enums.EModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VehicleResDTO {
    private Long id;
    private String name;
    private String modelCode;

    public VehicleResDTO(Long id, String name, EModel modelCode) {
        this.id = id;
        this.name = name;
        this.modelCode = modelCode.getValue();
    }
}
