package com.cg.domain.dto.customer;

import com.cg.domain.entity.customer.CustomerLocation;
import com.cg.utils.AppUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerResDTO {
    private String id;
    private String fullName;
    private String identificationNumber;
    private String email;
    private LocalDate dob;
    private String phone;
    private LocalDateTime createdAt;
    private CustomerAvatarResDTO customerAvatarResDTO;
    private CustomerLocationResDTO customerLocation;

    public CustomerResDTO(String id, String fullName, String identificationNumber, String email, LocalDate dob, String phone, LocalDateTime createdAt, CustomerLocation customerLocation) {
        this.id = id;
        this.fullName = fullName;
        this.identificationNumber = identificationNumber;
        this.email = email;
        this.dob = dob;
        this.phone = phone;
        this.createdAt = createdAt;
        this.customerLocation = customerLocation.toCustomerLocationResDTO();
    }
}
