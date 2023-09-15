package com.cg.domain.dto.car;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CarCreateResDTO {

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

}
