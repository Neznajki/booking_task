package com.booking.dto;

import com.booking.db.entity.HotelRoom;
import com.booking.validator.constraint.DateSelectionConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@DateSelectionConstraint
public class DatePickedDTO extends DateSelectionDTO {
    private final HotelRoom room;
}
