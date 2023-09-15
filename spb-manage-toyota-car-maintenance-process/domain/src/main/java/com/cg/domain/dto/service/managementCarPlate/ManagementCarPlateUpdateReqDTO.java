package com.cg.domain.dto.service.managementCarPlate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ManagementCarPlateUpdateReqDTO implements Validator {

    private String carNumberPlate;
    private String username;

    @Override
    public boolean supports(Class<?> clazz) {
        return ManagementCarPlateUpdateReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ManagementCarPlateUpdateReqDTO managementCarPlateUpdateReqDTO = (ManagementCarPlateUpdateReqDTO) target;
        String carPlate = managementCarPlateUpdateReqDTO.carNumberPlate;
        String username = managementCarPlateUpdateReqDTO.username;

        if(carPlate == null) {
            errors.rejectValue("carPlate", "carPlate.null", "Biển số xe là bắt buộc");
        } else {
            if (carPlate.trim().length() == 0) {
                errors.rejectValue("carPlate", "carPlate.empty", "Biển số xe là không được để trống");
            } else {
                if (!java.util.regex.Pattern.matches("^[1-9]{2}[A-Z][0-9]{5}$", carPlate)) {
                    errors.rejectValue("carPlate", "carPlate.valid", "Biển số xe không đúng định dạng");
                }
            }
        }
        if(username == null) {
            errors.rejectValue("username", "username.null", "Username là bắt buộc");
        } else {
            if (username.trim().length() == 0) {
                errors.rejectValue("username", "username.empty", "Username là không được để trống");
            } else {
                if (!java.util.regex.Pattern.matches("^(\\\\+?84|0)(3[2-9]|5[2689]|7[06-9]|8[1-9]|9[0-9])[0-9]{7}$", username)) {
                    errors.rejectValue("username", "username.valid", "Username không đúng định dạng");
                }
            }
        }
    }
}
