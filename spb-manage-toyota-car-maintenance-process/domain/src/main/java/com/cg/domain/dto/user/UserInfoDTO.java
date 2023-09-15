package com.cg.domain.dto.user;

import com.cg.domain.entity.user.UserRole;
import com.cg.domain.enums.EUserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserInfoDTO {

    private String username;
    private String fullName;
    private EUserRole roleName;
    private String roleCode;


    public UserInfoDTO(String username, String fullName, UserRole userRole) {
        this.username = username;
        this.fullName = fullName;
        this.roleName = userRole.toRoleDTO().getName();
        this.roleCode = userRole.toRoleDTO().getCode();
    }
}
