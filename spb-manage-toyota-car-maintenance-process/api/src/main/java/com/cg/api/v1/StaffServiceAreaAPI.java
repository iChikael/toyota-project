package com.cg.api.v1;

import com.cg.domain.dto.service.staffServiceArea.StaffServiceAreaCreateReqDTO;
import com.cg.domain.entity.service.*;
import com.cg.domain.entity.staff.Staff;
import com.cg.domain.entity.user.User;
import com.cg.exception.DataInputException;
import com.cg.service.service.IServiceAreaService;
import com.cg.service.service.IStaffServiceAreaService;
import com.cg.service.staff.IStaffService;
import com.cg.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
@RequestMapping("/api/v1/staff-serviceAreas")
public class StaffServiceAreaAPI {

    @Autowired
    private IStaffServiceAreaService staffServiceAreaService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IStaffService staffService;

    @Autowired
    private IServiceAreaService serviceAreaService;

    @PostMapping
    public ResponseEntity<?> createStaffServiceArea(@RequestBody StaffServiceAreaCreateReqDTO staffServiceAreaCreateReqDTO, BindingResult bindingResult) {

        new StaffServiceAreaCreateReqDTO().validate(staffServiceAreaCreateReqDTO, bindingResult);

        User user = userService.getByUsername(staffServiceAreaCreateReqDTO.getUsername());

        Optional<Staff> staffOptional = staffService.findByUser(user);

        if (staffOptional.isEmpty()) {
            throw new DataInputException("Không tìm thấy thông tin nhân viên!");
        }

        Optional<ServiceArea> serviceAreaOptional = serviceAreaService.findByIdAndDeletedIsFalse(Long.parseLong(staffServiceAreaCreateReqDTO.getServiceAreaId()));

        if (serviceAreaOptional.isEmpty()) {
            throw new DataInputException("Không tìm thấy khu vực dịch vụ!");
        }

        StaffServiceArea staffServiceArea = new StaffServiceArea();
        staffServiceArea.setId(null);
        staffServiceArea.setStaff(staffOptional.get());
        staffServiceArea.setServiceArea(serviceAreaOptional.get());

        staffServiceAreaService.save(staffServiceArea);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
