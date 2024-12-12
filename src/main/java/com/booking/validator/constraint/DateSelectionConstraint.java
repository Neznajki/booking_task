package com.booking.validator.constraint;

import com.booking.validator.DateSelectionValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DateSelectionValidator.class)
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface DateSelectionConstraint {
    String message() default "please select arrival date before leaving";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
