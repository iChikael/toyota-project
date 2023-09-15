package com.cg.domain.dto.user;


import com.cg.domain.entity.customer.Customer;
import com.cg.domain.entity.customer.CustomerLocation;
import com.cg.domain.entity.staff.Staff;
import com.cg.domain.entity.staff.StaffLocation;
import com.cg.domain.entity.user.User;
import com.cg.domain.entity.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRegisterReqDTO {

    @Pattern(regexp = "^(\\\\+?84|0)(3[2-9]|5[2689]|7[06-9]|8[1-9]|9[0-9])[0-9]{7}$", message = "Username hoặc số điện thoại không hợp lệ!")
    private String username;

    @NotBlank(message = "Vui lòng nhập mật khẩu!")
    @Size(min = 3, max = 50, message = "Độ dài mật khẩu nằm trong khoảng 3-50 ký tự!")
    private String password;

    @Pattern(regexp = "^[0-9]{12}$", message = "Số CCCD không hợp lệ!")
    private String identificationNumber;
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



    private String userRoleCode;


    public User toUser(String id, UserRole userRole) {
        return new User()
                .setId(id)
                .setUsername(username)
                .setPassword(password)
                .setUserRole(userRole);
    }

    public Staff toStaff(String id) {
        return new Staff()
                .setId(id)
                .setFullName(fullName)
                .setIdentificationNumber(identificationNumber)
                .setEmail(email)
                .setDob(convertStringToLocalDate(dob))
                .setPhone(username);
    }
    public Customer toCustomer(String id) {
        return new Customer()
                .setId(id)
                .setFullName(fullName)
                .setIdentificationNumber(identificationNumber)
                .setEmail(email)
                .setDob(convertStringToLocalDate(dob))
                .setPhone(username);
    }

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

    public LocalDate convertStringToLocalDate(String str) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(str, formatter);
    }
}
