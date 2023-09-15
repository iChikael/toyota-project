package com.cg.domain.entity.customer;

import com.cg.domain.dto.customer.CustomerResDTO;
import com.cg.domain.dto.customer.CustomerUpdateInfoResDTO;
import com.cg.domain.entity.BaseEntity;
import com.cg.domain.entity.user.User;
import com.cg.utils.AppUtils;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "identification_number", nullable = false)
    private String identificationNumber;

    private String email;

    private LocalDate dob;

    @Column(nullable = false)
    private String phone;

    @OneToOne
    @JoinColumn(name = "customer_location_id", referencedColumnName = "id", nullable = false)
    private CustomerLocation customerLocation;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    public CustomerUpdateInfoResDTO toCustomerUpdateInfoResDTO() {
        return new CustomerUpdateInfoResDTO()
                .setId(id)
                .setFullName(fullName)
                .setIdentificationNumber(identificationNumber)
                .setEmail(email)
                .setDob(dob)
                .setPhone(phone)
                .setCustomerLocation(customerLocation.toCustomerLocationResDTO());
    }

    public CustomerResDTO toCustomerResDTO() {
        return new CustomerResDTO()
                .setId(id)
                .setFullName(fullName)
                .setIdentificationNumber(identificationNumber)
                .setEmail(email)
                .setDob(dob)
                .setPhone(phone)
                .setCreatedAt(getCreatedAt())
                .setCustomerLocation(customerLocation.toCustomerLocationResDTO());
    }
}
