package com.booking.validator;

import com.booking.dto.DateSelectionDTO;
import com.booking.validator.constraint.DateSelectionConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateSelectionValidator implements ConstraintValidator<DateSelectionConstraint, DateSelectionDTO> {
    @Override
    public void initialize(DateSelectionConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(DateSelectionDTO dateSelectionDTO, ConstraintValidatorContext constraintValidatorContext) {
        return dateSelectionDTO.getArrivalDate().before(dateSelectionDTO.getLeavingDate());
    }
}
