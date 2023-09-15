package com.cg.api.v1;

import com.cg.domain.dto.service.serviceArea.*;
import com.cg.domain.entity.service.ServiceArea;
import com.cg.exception.DataInputException;
import com.cg.service.service.IServiceAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/service-areas")
public class ServiceAreaAPI {

    @Autowired
    private IServiceAreaService serviceAreaService;


    @GetMapping
    public ResponseEntity<?> getAll() {

        List<ServiceAreaResDTO> serviceAreaResDTOS = serviceAreaService.findAllServiceAreasDTO();

        return new ResponseEntity<>(serviceAreaResDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {

        Optional<ServiceArea> serviceAreaOptional = serviceAreaService.findById(id);

        if (serviceAreaOptional.isPresent()) {
            ServiceArea serviceArea = serviceAreaOptional.get();

            ServiceAreaResDTO serviceAreaResDTO = serviceArea.toServiceAreaResDTO();

            return new ResponseEntity<>(serviceAreaResDTO, HttpStatus.OK);
        } else {
            throw new DataInputException("Khu vực sửa chữa không tồn tại !");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ServiceAreaCreateReqDTO serviceAreaCreateReqDTO) {

        ServiceArea serviceArea = serviceAreaService.createServiceArea(serviceAreaCreateReqDTO);

        ServiceAreaCreateResDTO serviceAreaCreateResDTO = serviceArea.toServiceAreaCreateResDTO();

        return new ResponseEntity<>(serviceAreaCreateResDTO, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody ServiceAreaUpdateReqDTO serviceAreaUpdateReqDTO) {

        Optional<ServiceArea> serviceAreaOptional = serviceAreaService.findById(Long.parseLong(id));


        if (serviceAreaOptional.isPresent()) {

            ServiceArea serviceArea = serviceAreaService.updateServiceArea(serviceAreaOptional.get(), serviceAreaUpdateReqDTO);

            ServiceAreaUpdateResDTO serviceAreaUpdateResDTO = serviceArea.toServiceAreaUpdateResDTO();

            return new ResponseEntity<>(serviceAreaUpdateResDTO, HttpStatus.OK);

        } else {
            throw new DataInputException("Không tìm thấy thông tin khu vực!");
        }
    }

    @PatchMapping("/update-current-capacity")
    public ResponseEntity<?> updateCurrentCapacity( @RequestBody ServiceAreaUpdateReqDTO serviceAreaUpdateReqDTO) {
        if (serviceAreaUpdateReqDTO.getCurrentCapacity() > 5 || serviceAreaUpdateReqDTO.getCurrentCapacity() < 0) {
            throw new DataInputException("Sức chứa không hợp lệ!");
        } else {
            Optional<ServiceArea> serviceAreaOptional = serviceAreaService.findById(serviceAreaUpdateReqDTO.getId());

            if (serviceAreaOptional.isPresent()) {

                ServiceArea serviceArea = serviceAreaOptional.get();
                serviceArea.setCurrentCapacity(serviceAreaUpdateReqDTO.getCurrentCapacity());
                serviceAreaService.save(serviceArea);
                ServiceAreaUpdateResDTO serviceAreaUpdateResDTO = serviceArea.toServiceAreaUpdateResDTO();

                return new ResponseEntity<>(serviceAreaUpdateResDTO, HttpStatus.OK);

            } else {
                throw new DataInputException("Không tìm thấy thông tin khu vực!");
            }
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        Optional<ServiceArea> serviceAreaOptional = serviceAreaService.findById(id);
        if (serviceAreaOptional.isPresent()) {

            ServiceArea serviceArea = serviceAreaOptional.get();

            serviceArea.setDeleted(true);

            serviceAreaService.save(serviceArea);

            return new ResponseEntity<>(HttpStatus.ACCEPTED);

        } else {
            throw new DataInputException("Không tìm thấy thông tin khu vực!");
        }
    }
}
