package com.cg.domain.dto.car;

import com.cg.domain.entity.car.CarAvatar;
import com.cg.domain.entity.car.Vehicle;
import com.cg.domain.enums.EFuel;
import com.cg.domain.enums.EOrigin;
import com.cg.domain.enums.ESeat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CarResDTO {
    private Long id;
    private String code;
    private String title;
    private BigDecimal price;
    private String description;
    private String seatCode;
    private String fuelCode;
    private String originCode;
    private VehicleResDTO vehicle;
    private CarAvatarResDTO carAvatar;
    public CarResDTO(Long id, String code, String title, BigDecimal price, String description, ESeat seatCode, EFuel fuelCode, EOrigin originCode, Vehicle vehicle, CarAvatar carAvatar) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.price = price;
        this.description = description;
        this.seatCode = seatCode.getValue();
        this.fuelCode = fuelCode.getValue();
        this.originCode = originCode.getValue();
        this.vehicle = vehicle.toVehicleResDTO();
        this.carAvatar = carAvatar.toCarAvatarResDTO();
    }
}
