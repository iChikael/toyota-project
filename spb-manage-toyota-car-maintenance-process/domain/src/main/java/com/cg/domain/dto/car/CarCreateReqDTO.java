package com.cg.domain.dto.car;

import com.cg.domain.entity.car.Car;
import com.cg.domain.entity.car.Vehicle;
import com.cg.domain.enums.EFuel;
import com.cg.domain.enums.EOrigin;
import com.cg.domain.enums.ESeat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CarCreateReqDTO implements Validator {

    private String code;
    private String title;
    private String price;
    private String description;
    private String seatCode;
    private String fuelCode;
    private String originCode;
    private Long vehicleId;
    private MultipartFile carAvatar;

    public Car toCar(Vehicle vehicle) {
        return new Car()
                .setCode(code)
                .setTitle(title)
                .setPrice(BigDecimal.valueOf(Long.parseLong(price)))
                .setDescription(description)
                .setSeatCode(ESeat.getESeatByName(seatCode))
                .setFuelCode(EFuel.getEFuelByName(fuelCode))
                .setOriginCode(EOrigin.getEOriginByName(originCode))
                .setVehicle(vehicle);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return CarCreateReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CarCreateReqDTO carCreateReqDTO = (CarCreateReqDTO) target;
    }
}
