package com.cg.domain.entity.service;

import com.cg.domain.dto.service.managementCarPlate.ManagementCarPlateCreateResDTO;
import com.cg.domain.dto.service.managementCarPlate.ManagementCarPlateUpdateResDTO;
import com.cg.domain.entity.BaseEntity;
import com.cg.domain.entity.car.Car;
import com.cg.domain.entity.customer.Customer;
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
@Table(name = "management_car_plates", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"customer_id", "car_id", "car_number_plate"}, name = "UK_management_car_plate")
})
public class ManagementCarPlate extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "car_number_plate", nullable = false, updatable = false)
    private String carNumberPlate;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Car car;

    public ManagementCarPlateCreateResDTO toManagementCarPlateCreateResDTO() {
        return new ManagementCarPlateCreateResDTO()
                .setCarPlate(carNumberPlate)
                .setCustomer(customer.toCustomerResDTO())
                .setCar(car.toCarResDTO())
                ;
    }

    public ManagementCarPlateUpdateResDTO toManagementCarPlateUpdateResDTO() {
        return new ManagementCarPlateUpdateResDTO()
                .setCarPlate(carNumberPlate)
                .setCustomer(customer.toCustomerResDTO())
                .setCar(car.toCarResDTO())
                ;
    }
}
