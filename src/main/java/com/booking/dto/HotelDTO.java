package com.booking.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class HotelDTO {
    @Size.List({
        @Size(min = 4, message = "validation.name.size.too_short"),
        @Size(max = 64, message = "validation.name.size.too_long")
    })
    private String name = "";
    @Size.List({
            @Size(min = 4, message = "validation.name.size.too_short"),
            @Size(max = 64, message = "validation.name.size.too_long")
    })
    private String address = "";
    @Positive
    private Integer floors = 2;
}
