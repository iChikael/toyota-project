package com.cg.domain.dto.staff;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StaffUpdateInfoResDTO {
    private String id;
    private String fullName;
    private String identificationNumber;
    private String email;
    private LocalDate dob;
    private String phone;
    private StaffAvatarResDTO staffAvatar;
    private StaffLocationResDTO staffLocation;
}
