package com.xworkz.orderprocessing.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class StatusValidator implements ConstraintValidator<ValidStatus, String> {

    private final Set<String> allowed = Set.of(
            "PENDING",
            "PROCESSING",
            "COMPLETED",
            "CANCELLED"
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return allowed.contains(value.toUpperCase());
    }
}
