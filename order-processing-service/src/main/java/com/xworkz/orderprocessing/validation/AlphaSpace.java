package com.xworkz.orderprocessing.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AlphaSpaceValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AlphaSpace {

    String message() default "Customer name should contain only alphabets";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
