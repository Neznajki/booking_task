package com.booking.validator.constraint;

import com.booking.validator.HotelRoomValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = HotelRoomValidator.class)
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface HotelRoomConstraint {
    String message() default "not used";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
