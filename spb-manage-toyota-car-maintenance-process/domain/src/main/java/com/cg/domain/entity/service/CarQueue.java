package com.cg.domain.entity.service;

import com.cg.domain.dto.service.carqueue.CarQueueCreateResDTO;
import com.cg.domain.dto.service.carqueue.CarQueueResDTO;
import com.cg.domain.dto.service.carqueue.CarQueueUpdateResDTO;
import com.cg.domain.entity.BaseEntity;
import com.cg.domain.enums.EStatusCarQueue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "car_queue")
public class CarQueue extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "car_number_plates", nullable = false)
    private String carNumberPlates;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_name", length = 50)
    private EStatusCarQueue status;

    public CarQueueResDTO toCarQueueResDTO() {
        return new CarQueueResDTO()
                .setId(id)
                .setCarNumberPlates(carNumberPlates)
                .setFullName(fullName)
                .setPhone(phone)
                .setStatus(status.getValue());
    }

    public CarQueueCreateResDTO toCarQueueCreateResDTO() {
        return new CarQueueCreateResDTO()
                .setId(id)
                .setCarNumberPlates(carNumberPlates)
                .setFullName(fullName)
                .setPhone(phone)
                .setStatus(status.getValue());
    }

    public CarQueueUpdateResDTO toCarQueueUpdateResDTO() {
        return new CarQueueUpdateResDTO()
                .setId(id)
                .setCarNumberPlates(carNumberPlates)
                .setFullName(fullName)
                .setPhone(phone)
                .setStatus(status.getValue());
    }
}
