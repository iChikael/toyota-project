package com.cg.domain.dto.staff;


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
public class StaffUpdatePasswordReqDTO implements Validator {
    private String password;

    @Override
    public boolean supports(Class<?> clazz) {
        return StaffUpdatePasswordReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        StaffUpdatePasswordReqDTO staffUpdatePasswordReqDTO = (StaffUpdatePasswordReqDTO) target;
    }
}
