package com.cg.domain.entity.car;

import com.cg.domain.dto.car.VehicleResDTO;
import com.cg.domain.entity.BaseEntity;
import com.cg.domain.enums.EModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "vehicles")
public class Vehicle extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "model_code", nullable = false, length = 50)
    private EModel modelCode;

    @OneToMany(mappedBy = "vehicle")
    private List<Car> cars;

    public VehicleResDTO toVehicleResDTO() {
        return new VehicleResDTO()
                .setId(id)
                .setName(name)
                .setModelCode(modelCode.getValue());

    }
}
