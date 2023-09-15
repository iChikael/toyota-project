package com.cg.domain.dto.customer;

import com.cg.domain.entity.customer.CustomerLocation;
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
public class CustomerUpdateInfoReqDTO implements Validator {

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

    public CustomerLocation toCustomerLocation(Long idCustomerLocation) {
        return new CustomerLocation()
                .setId(idCustomerLocation)
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
        return CustomerUpdateInfoReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CustomerUpdateInfoReqDTO customerUpdateInfoReqDTO = (CustomerUpdateInfoReqDTO) target;
    }
}
