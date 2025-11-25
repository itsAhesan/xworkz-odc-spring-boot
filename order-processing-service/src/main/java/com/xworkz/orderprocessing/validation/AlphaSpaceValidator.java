package com.xworkz.orderprocessing.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AlphaSpaceValidator implements ConstraintValidator<AlphaSpace, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return value.matches("^[A-Za-z ]+$");
    }


}
