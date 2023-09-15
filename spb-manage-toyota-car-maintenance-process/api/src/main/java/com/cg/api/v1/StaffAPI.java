package com.cg.api.v1;


import com.cg.domain.dto.staff.StaffResDTO;
import com.cg.domain.dto.staff.StaffUpdateInfoReqDTO;
import com.cg.domain.dto.staff.StaffUpdateInfoResDTO;
import com.cg.domain.dto.staff.StaffUpdatePasswordReqDTO;
import com.cg.domain.entity.staff.Staff;
import com.cg.domain.entity.staff.StaffAvatar;
import com.cg.domain.entity.user.User;
import com.cg.exception.DataInputException;
import com.cg.service.staff.IStaffAvatarService;
import com.cg.service.staff.IStaffService;
import com.cg.service.user.IUserRoleService;
import com.cg.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/staffs")
public class StaffAPI {

    @Autowired
    private IStaffService staffService;

    @Autowired
    private IStaffAvatarService staffAvatarService;

    @Autowired
    private IUserService userService;

    @GetMapping
    public ResponseEntity<List<?>> getAllStaffDTO() {
        List<StaffResDTO> staffResDTOList = new ArrayList<>();

        List<StaffResDTO> staffResDTOS = staffService.findAllStaffResDTO();

        if (staffResDTOS.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        for (StaffResDTO staffResDTO : staffResDTOS) {
            Optional<StaffAvatar> staffAvatar = staffAvatarService.findStaffAvatarByStaffIdAndDeletedIsFalse(staffResDTO.getId());
            staffResDTO.setStaffAvatar(staffAvatar.get().toStaffAvatarResDTO());
            staffResDTOList.add(staffResDTO);
        }

        return new ResponseEntity<>(staffResDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStaffByIdDTO(@PathVariable String id) {
        Optional<StaffResDTO> staffResDTOOptional = staffService.findStaffByIdResDTO(id);

        if (staffResDTOOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        StaffResDTO staffResDTO = staffResDTOOptional.get();

        Optional<StaffAvatar> staffAvatarOptional = staffAvatarService.findStaffAvatarByStaffIdAndDeletedIsFalse(staffResDTO.getId());

        staffResDTO.setStaffAvatar(staffAvatarOptional.get().toStaffAvatarResDTO());

        return new ResponseEntity<>(staffResDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/export-to-excel")
    public ResponseEntity<StreamingResponseBody> exportStaffsFromDBtoExCel(HttpServletResponse response) {

        StreamingResponseBody responseBody = staffService.exportToExcel(response);

        return ResponseEntity.ok(responseBody);

    }

    @PostMapping(path = "/import-to-db")
    public ResponseEntity<?> importStaffsFromExcelToDb(@RequestPart MultipartFile file) {

        staffService.importToDb(file);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/update-password/{id}")
    public ResponseEntity<?> updatePasswordStaff(@PathVariable String id, @RequestBody StaffUpdatePasswordReqDTO staffUpdatePasswordReqDTO, BindingResult bindingResult) {

        new StaffUpdatePasswordReqDTO().validate(staffUpdatePasswordReqDTO, bindingResult);

        Optional<Staff> staffOptional = staffService.findById(id);

        if (staffOptional.isPresent()) {

            User user = userService.getByUsername(staffOptional.get().getUser().getUsername());

            user.setPassword(staffUpdatePasswordReqDTO.getPassword());

            userService.save(user);

            StaffResDTO staffResDTO = staffOptional.get().toStaffResDTO();

            StaffAvatar staffAvatar = staffAvatarService.findStaffAvatarByStaffIdAndDeletedIsFalse(staffOptional.get().getId()).get();

            staffResDTO.setStaffAvatar(staffAvatar.toStaffAvatarResDTO());

            return new ResponseEntity<>(staffResDTO, HttpStatus.OK);
        } else {
            throw new DataInputException("Không tìm thấy thông tin khách hàng!");
        }
    }

    @PatchMapping("/update-info/{id}")
    public ResponseEntity<?> updateInfoCustomer(@PathVariable String id, @ModelAttribute StaffUpdateInfoReqDTO staffUpdateInfoReqDTO, MultipartFile staffAvatar, BindingResult bindingResult) {

        new StaffUpdateInfoReqDTO().validate(staffUpdateInfoReqDTO, bindingResult);

        Optional<Staff> staffOptional = staffService.findById(id);

        if (staffOptional.isPresent()) {

            Staff staff = staffService.updateStaff(staffOptional.get(), staffUpdateInfoReqDTO);

            StaffAvatar staffAvatarNew;

            if (staffAvatar != null && staffAvatar.getSize() != 0) {

                Optional<StaffAvatar> staffAvatarOptional = staffAvatarService.findStaffAvatarByStaffIdAndDeletedIsFalse(staff.getId());

                staffAvatarOptional.get().setDeleted(true);

                staffAvatarNew = staffAvatarService.createStaffAvatar(staffAvatar, staff);
            } else {

                Optional<StaffAvatar> staffAvatarOptional = staffAvatarService.findStaffAvatarByStaffIdAndDeletedIsFalse(staff.getId());

                staffAvatarNew = staffAvatarOptional.get().setStaff(staff);
            }

            StaffUpdateInfoResDTO staffUpdateInfoResDTO = staff.toStaffUpdateInfoResDTO();

            staffUpdateInfoResDTO.setStaffAvatar(staffAvatarNew.toStaffAvatarResDTO());

            return new ResponseEntity<>(staffUpdateInfoResDTO, HttpStatus.OK);
        } else {
            throw new DataInputException("Không tìm thấy thông tin khách hàng!");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String id) {

        Optional<Staff> staffOptional = staffService.findById(id);

        if (staffOptional.isPresent()) {

            Staff staff = staffOptional.get();

            staffService.delete(staff);

            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new DataInputException("Không tìm thấy thông tin khách hàng!");
        }
    }
}
