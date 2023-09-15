package com.cg.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ValidateUtils {
    public static final String NUMBER_REGEX = "\\d+";
    public static final String CAR_PLATE_REGEX = "^[1-9]{2}[A-Z][0-9]{5}$";


    public boolean isNumberValid(String number) {
        return Pattern.compile(NUMBER_REGEX).matcher(number).matches();
    }

    public boolean isCarPlateValid (String carPlate) {
        return Pattern.compile(CAR_PLATE_REGEX).matcher(carPlate).matches();
    }

    public boolean isLongParsable(String number) {
        try {
            Long.parseLong(number);
            return true;
        } catch (final NumberFormatException e) {
            return false;
        }
    }
}
