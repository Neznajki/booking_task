package com.booking.dto;

import com.booking.helper.DateTimeHelper;
import com.booking.validator.constraint.DateSelectionConstraint;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@DateSelectionConstraint
public class DateSelectionDTO {
    @DateTimeFormat(pattern = DateTimeHelper.DATE_FORMAT)
    protected Date arrivalDate;
    @DateTimeFormat(pattern = DateTimeHelper.DATE_FORMAT)
    protected Date leavingDate;
    @Range(min = 1, max = 9, message = "persons should be between 1 and 9")
    protected Integer persons = 1;
}
