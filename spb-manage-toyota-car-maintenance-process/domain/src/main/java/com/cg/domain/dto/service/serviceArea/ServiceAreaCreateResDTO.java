package com.cg.domain.dto.service.serviceArea;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ServiceAreaCreateResDTO {
    private Long id;
    private String name;
    private Long capacity;
    private Long currentCapacity;
    private String status;
}
