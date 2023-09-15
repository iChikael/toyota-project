package com.cg.domain.dto.staff;


import com.cg.domain.entity.staff.StaffLocation;
import com.cg.domain.entity.user.User;
import com.cg.domain.enums.EUserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StaffResDTO {

    private String id;
    private String fullName;
    private String identificationNumber;
    private String email;
    private LocalDate dob;
    private String phone;
    private StaffAvatarResDTO staffAvatar;
    private StaffLocationResDTO staffLocation;
    private String role;
    public StaffResDTO(String id, String fullName, String identificationNumber, String email, LocalDate dob, String phone, StaffLocation staffLocation, User user) {
        this.id = id;
        this.fullName = fullName;
        this.identificationNumber = identificationNumber;
        this.email = email;
        this.dob = dob;
        this.phone = phone;
        this.staffLocation = staffLocation.toStaffLocationResDTO();
        this.setRole(user.getUserRole().getCode());
    }
}
