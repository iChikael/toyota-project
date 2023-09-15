package com.cg.domain.entity.service;

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
@Table(name = "management_car_plate_car_queue", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"management_car_plate_id", "car_queue_id"}, name = "UK_management_car_plate_car_queue")
})
public class ManagementCarPlateCarQueue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "car_queue_id", referencedColumnName = "id")
    private CarQueue carQueue;

    @OneToOne
    @JoinColumn(name = "management_car_plate_id", referencedColumnName = "id")
    private ManagementCarPlate managementCarPlate;
}
