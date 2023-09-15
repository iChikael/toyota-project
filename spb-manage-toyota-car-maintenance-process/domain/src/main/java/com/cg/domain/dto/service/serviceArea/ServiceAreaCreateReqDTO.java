package com.cg.domain.dto.service.serviceArea;

import com.cg.domain.entity.service.ServiceArea;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ServiceAreaCreateReqDTO {
    private String name;
    private Long capacity;
    public ServiceArea toServiceArea(){
        return new ServiceArea()
                .setCapacity(capacity)
                .setName(name)
                ;
    }

}
