package com.cg.domain.dto.service.managementCarPlate;

import com.cg.domain.dto.car.CarResDTO;
import com.cg.domain.dto.customer.CustomerResDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ManagementCarPlateUpdateResDTO {

    private String carPlate;
    private CarResDTO car;
    private CustomerResDTO customer;
}
