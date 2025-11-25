package com.xworkz.orderprocessing.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StatusValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidStatus {

    String message() default "Invalid order status";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
