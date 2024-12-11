package com.booking.validator;

import com.booking.dto.HotelRoomDTO;
import com.booking.validator.constraint.HotelRoomConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HotelRoomValidator implements ConstraintValidator<HotelRoomConstraint, HotelRoomDTO> {
    @Override
    public void initialize(HotelRoomConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(HotelRoomDTO hotelRoom, ConstraintValidatorContext constraintValidatorContext) {
        if (hotelRoom.getFloor() < -1) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("floors must be more than -1").addConstraintViolation();
            return false;
        } else {
            Integer floors = hotelRoom.getHotel().getFloors();
            if (floors < hotelRoom.getFloor()) {
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate(String.format("floors should be not higher than %d", floors)).addConstraintViolation();
                return false;
            }
        }

        return true;
    }
}
