package com.cg.domain.dto.service.serviceArea;

import com.cg.domain.entity.service.ServiceArea;
import com.cg.domain.enums.EStatusServiceArea;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ServiceAreaUpdateReqDTO {
    private Long id;
    private String name;
    private Long capacity;
    private Long currentCapacity;
    private String status;

    public ServiceArea toServiceArea(){
        return new ServiceArea()
                .setId(id)
                .setCapacity(capacity)
                .setCurrentCapacity(currentCapacity)
                .setName(name)
                .setStatus(EStatusServiceArea.getEStatusServiceAreaByName(status))
                ;
    }
}
