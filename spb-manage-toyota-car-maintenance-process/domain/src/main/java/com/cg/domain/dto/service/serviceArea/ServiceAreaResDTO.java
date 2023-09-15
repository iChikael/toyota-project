package com.cg.domain.dto.service.serviceArea;

import com.cg.domain.enums.EStatusServiceArea;
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
    private Long currentCapacity;
    private String status;

    public ServiceAreaResDTO (Long id, String name, Long capacity, Long currentCapacity, EStatusServiceArea eStatusServiceArea) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.currentCapacity = currentCapacity;
        this.status = eStatusServiceArea.getValue();
    }
}
