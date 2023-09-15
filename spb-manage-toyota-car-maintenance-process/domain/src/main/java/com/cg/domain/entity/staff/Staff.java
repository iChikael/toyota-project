package com.cg.domain.entity.staff;

import com.cg.domain.dto.staff.StaffResDTO;
import com.cg.domain.dto.staff.StaffUpdateInfoResDTO;
import com.cg.domain.entity.BaseEntity;
import com.cg.domain.entity.user.User;
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
@Table(name = "staffs")
public class Staff extends BaseEntity {

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

    private String phone;

    @OneToOne
    @JoinColumn(name = "staff_location_id", referencedColumnName = "id", nullable = false)
    private StaffLocation staffLocation;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public StaffResDTO toStaffResDTO() {
        return new StaffResDTO()
                .setId(id)
                .setFullName(fullName)
                .setIdentificationNumber(identificationNumber)
                .setEmail(email)
                .setDob(dob)
                .setPhone(phone)
                .setStaffLocation(staffLocation.toStaffLocationResDTO())
                .setRole(user.getUserRole().getCode());
    }

    public StaffUpdateInfoResDTO toStaffUpdateInfoResDTO() {
        return new StaffUpdateInfoResDTO()
                .setId(id)
                .setFullName(fullName)
                .setIdentificationNumber(identificationNumber)
                .setEmail(email)
                .setDob(dob)
                .setPhone(phone)
                .setStaffLocation(staffLocation.toStaffLocationResDTO());
    }
}
