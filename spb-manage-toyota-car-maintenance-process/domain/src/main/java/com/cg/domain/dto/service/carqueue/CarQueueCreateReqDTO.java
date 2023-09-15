package com.cg.domain.dto.service.carqueue;


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
public class CarQueueCreateReqDTO implements Validator {

    private String carNumberPlates;
    private String fullName;
    private String phone;

    @Override
    public boolean supports(Class<?> clazz) {
        return CarQueueCreateReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CarQueueCreateReqDTO carQueueCreateReqDTO = (CarQueueCreateReqDTO) target;
        String carPlate = carQueueCreateReqDTO.carNumberPlates;
        String phone = carQueueCreateReqDTO.phone;

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
        if(phone == null) {
            errors.rejectValue("phone", "phone.null", "Số điện thoại là bắt buộc");
        } else {
            if (phone.trim().length() == 0) {
                errors.rejectValue("phone", "phone.empty", "Số điện thoại là không được để trống");
            } else {
                if (!java.util.regex.Pattern.matches("^(\\\\+?84|0)(3[2-9]|5[2689]|7[06-9]|8[1-9]|9[0-9])[0-9]{7}$", phone)) {
                    errors.rejectValue("phone", "phone.valid", "Số điện thoại không đúng định dạng");
                }
            }
        }
    }
}
