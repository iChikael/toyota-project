package com.cg.domain.dto.staff;

import com.cg.domain.entity.staff.StaffLocation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StaffUpdateInfoReqDTO implements Validator {

    private String fullName;
    private String email;
    private String dob;


    private String provinceId;
    private String provinceName;
    private String districtId;
    private String districtName;
    private String wardId;
    private String wardName;
    private String address;

    public StaffLocation toStaffLocation(Long idStaffLocation) {
        return new StaffLocation()
                .setId(idStaffLocation)
                .setProvinceId(provinceId)
                .setProvinceName(provinceName)
                .setDistrictId(districtId)
                .setDistrictName(districtName)
                .setWardId(wardId)
                .setWardName(wardName)
                .setAddress(address)
                ;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return StaffUpdateInfoReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        StaffUpdateInfoReqDTO staffUpdateInfoReqDTO = (StaffUpdateInfoReqDTO) target;
    }
}
