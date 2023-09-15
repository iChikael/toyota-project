package com.cg.domain.dto.service.carqueue;

import com.cg.domain.dto.car.CarResDTO;
import com.cg.domain.entity.car.Car;
import com.cg.domain.enums.EStatusCarQueue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CarQueueResDTO {
    private Long id;
    private String carNumberPlates;
    private String fullName;
    private String phone;
    private String status;

    public CarQueueResDTO (Long id, String carNumberPlates, String fullName, String phone, EStatusCarQueue status) {
        this.id = id;
        this.carNumberPlates = carNumberPlates;
        this.fullName = fullName;
        this.phone = phone;
        this.status = status.getValue();
    }

}
