package com.cg.domain.dto.service.managementCarPlate;

import com.cg.domain.dto.car.CarResDTO;
import com.cg.domain.dto.customer.CustomerResDTO;
import com.cg.domain.entity.car.Car;
import com.cg.domain.entity.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ManagementCarPlateResDTO {
    private Long id;
    private String carName;
    private String carPlate;
    private String customerName;
    private String phone;
    private String email;
    private CarResDTO car;
    private CustomerResDTO customer;

    public ManagementCarPlateResDTO (Long id, String carPlate, Car car, Customer customer) {
        this.id = id;
        this.carName = car.getTitle();
        this.carPlate = carPlate;
        this.customerName = customer.getFullName();
        this.phone = customer.getPhone();
        this.email = customer.getEmail();
        this.car = car.toCarResDTO();
        this.customer = customer.toCustomerResDTO();
    }
}
