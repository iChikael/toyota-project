package com.cg.domain.entity.car;

import com.cg.domain.dto.car.CarCreateResDTO;
import com.cg.domain.dto.car.CarResDTO;
import com.cg.domain.dto.car.CarUpdateResDTO;
import com.cg.domain.entity.BaseEntity;
import com.cg.domain.enums.EFuel;
import com.cg.domain.enums.EOrigin;
import com.cg.domain.enums.ESeat;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@Entity
@Table(name = "cars")
public class Car extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private BigDecimal price;
    @Lob
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "seat_code", nullable = false, length = 50)
    private ESeat seatCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_code", nullable = false, length = 50)
    private EFuel fuelCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "origin_code", nullable = false, length = 50)
    private EOrigin originCode;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id", nullable = false)
    private Vehicle vehicle;


    @OneToOne
    @JoinColumn(name = "car_avatar_id", referencedColumnName = "id", nullable = false)
    private CarAvatar carAvatar;

    public CarCreateResDTO toCarCreateResDTO() {
        return new CarCreateResDTO()
                .setId(id)
                .setCode(code)
                .setTitle(title)
                .setPrice(price)
                .setDescription(description)
                .setSeatCode(seatCode.getValue())
                .setFuelCode(fuelCode.getValue())
                .setOriginCode(originCode.getValue())
                .setVehicle(vehicle.toVehicleResDTO())
                .setCarAvatar(carAvatar.toCarAvatarResDTO());
    }

    public CarUpdateResDTO toCarUpdateResDTO() {
        return new CarUpdateResDTO()
                .setId(id)
                .setCode(code)
                .setTitle(title)
                .setPrice(price)
                .setDescription(description)
                .setSeatCode(seatCode.getValue())
                .setFuelCode(fuelCode.getValue())
                .setOriginCode(originCode.getValue())
                .setVehicle(vehicle.toVehicleResDTO())
                .setCarAvatar(carAvatar.toCarAvatarResDTO());
    }

    public CarResDTO toCarResDTO() {
        return new CarResDTO()
                .setId(id)
                .setCode(code)
                .setTitle(title)
                .setPrice(price)
                .setDescription(description)
                .setSeatCode(seatCode.getValue())
                .setFuelCode(fuelCode.getValue())
                .setOriginCode(originCode.getValue())
                .setVehicle(vehicle.toVehicleResDTO())
                .setCarAvatar(carAvatar.toCarAvatarResDTO());
    }
}
