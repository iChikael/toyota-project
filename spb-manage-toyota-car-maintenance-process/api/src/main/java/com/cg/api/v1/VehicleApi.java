package com.cg.api.v1;

import com.cg.domain.dto.car.VehicleResDTO;
import com.cg.service.car.IVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicle")
public class VehicleApi {

    @Autowired
    private IVehicleService vehicleService;

    @GetMapping
    public ResponseEntity<?> getAll() {

        List<VehicleResDTO> vehicleResDTOS = vehicleService.getAllVehicleResDTO();

        return new ResponseEntity<>(vehicleResDTOS, HttpStatus.OK);
    }
}
