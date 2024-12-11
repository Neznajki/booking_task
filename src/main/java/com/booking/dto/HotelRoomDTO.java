package com.booking.dto;

import com.booking.db.entity.Hotel;
import com.booking.validator.constraint.HotelRoomConstraint;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
@HotelRoomConstraint
public class HotelRoomDTO {
    private Hotel hotel;
    @Size.List({
        @Size(min = 4, message = "validation.name.size.too_short"),
        @Size(max = 64, message = "validation.name.size.too_long")
    })
    private String name = "";
    @Range(min = 1, max = 9, message = "persons amount should be between 1 and 9")
    private Integer maxPersons = 1;
    @Positive
    private Integer floor = 1;

    public HotelRoomDTO() {}

    public HotelRoomDTO(Hotel hotel) {
        this.hotel = hotel;
    }
}
