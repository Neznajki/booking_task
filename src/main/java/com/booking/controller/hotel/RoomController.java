package com.booking.controller.hotel;

import com.booking.db.entity.AppUser;
import com.booking.db.entity.Hotel;
import com.booking.db.entity.HotelRoom;
import com.booking.dto.HotelRoomDTO;
import com.booking.dto.SuccessResponseDTO;
import com.booking.exception.JsonErrorException;
import com.booking.form.AddHotelRoomForm;
import com.booking.form.ListHotelRoomForm;
import com.booking.services.UserService;
import com.booking.services.hotel.room.HotelRoomService;
import com.booking.services.html.HtmlRenderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequiredArgsConstructor
@RequestMapping("/private/hotel/room")
public class RoomController {
    private final HtmlRenderService htmlRenderService;
    private final HotelRoomService hotelRoomService;
    private final UserService userService;

    @GetMapping("/{hotel}/list")
    public String list(@AuthenticationPrincipal AppUser userDetails, HttpServletRequest req, Hotel hotel) {
        return this.htmlRenderService.createResponse(
                new ListHotelRoomForm(
                        userDetails,
                        hotel,
                        hotelRoomService.getListContent(hotel)
                ),
                req
        );
    }

    @GetMapping("/add/{hotel}")
    public String add(@AuthenticationPrincipal AppUser userDetails, HttpServletRequest req, Hotel hotel) {
        return this.htmlRenderService.createResponse(new AddHotelRoomForm(userDetails, new HotelRoomDTO(hotel)), req);
    }

    @PostMapping(value = "/add/{hotel}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResponseDTO add(
            @Validated HotelRoomDTO hotelRoomDTO
    ) throws JsonErrorException {
        return this.hotelRoomService.add(hotelRoomDTO);
    }

    @PostMapping(value = "/delete/{hotelRoom}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RedirectView delete(
            @AuthenticationPrincipal AppUser userDetails,
            HotelRoom hotelRoom,
            HttpServletRequest req
    ) throws JsonErrorException {
        userService.checkDeletePermissions(userDetails, hotelRoom.getHotel());
        return this.hotelRoomService.delete(req, hotelRoom);
    }
}
