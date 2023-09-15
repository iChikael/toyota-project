package com.cg.domain.dto.role;

import com.cg.domain.entity.user.UserRole;
import com.cg.domain.enums.EUserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRoleDTO {

    @NotNull(message = "Bạn chưa chọn quyền!")
    private Long id;

    private String code;
    private EUserRole name;


    public UserRole toRole() {
        return new UserRole()
                .setId(id)
                .setCode(code)
                .setName(name)
                ;
    }
}
