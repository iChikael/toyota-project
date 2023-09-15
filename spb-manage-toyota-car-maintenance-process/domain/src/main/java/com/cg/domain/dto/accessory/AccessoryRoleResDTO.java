package com.cg.domain.dto.accessory;

import com.cg.domain.enums.EAccessoryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccessoryRoleResDTO {

    private Long id;
    private String code;
    private String codeName;


    public AccessoryRoleResDTO(Long id, String code, EAccessoryType codeName) {
        this.id = id;
        this.code = code;
        this.codeName = codeName.getValue();
    }
}
