package com.booking.controller.hotel;

import com.booking.db.entity.AppUser;
import com.booking.db.entity.Hotel;
import com.booking.dto.HotelDTO;
import com.booking.dto.SuccessResponseDTO;
import com.booking.exception.JsonErrorException;
import com.booking.form.AddHotelForm;
import com.booking.form.ListHotelRoomForm;
import com.booking.services.SessionMessageService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/private/hotel/room")
public class RoomController {
    private final HtmlRenderService htmlRenderService;
    private final HotelRoomService hotelService;
    private final SessionMessageService sessionMessageService;

    @GetMapping("/{hotelId}/list")
    public String list(@AuthenticationPrincipal AppUser userDetails, HttpServletRequest req, Hotel hotel) {
        return this.htmlRenderService.createResponse(
                new ListHotelRoomForm(
                        userDetails,
                        hotel,
                        sessionMessageService.getMessageForDisplay(req.getSession(), HtmlRenderService.SUCCESS_MESSAGE_INDEX),
                        hotelService.getListContent(userDetails)
                ),
                req
        );
    }

    @GetMapping("/add")
    public String add(@AuthenticationPrincipal AppUser userDetails, HttpServletRequest req) {
        return this.htmlRenderService.createResponse(new AddHotelForm(userDetails, new HotelDTO()), req);
    }

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResponseDTO add(
            @AuthenticationPrincipal AppUser userDetails,
            @Validated HotelDTO hotelDTO
    ) throws JsonErrorException {
        return this.hotelService.addHotel(userDetails, hotelDTO.getName(), hotelDTO.getAddress(), hotelDTO.getFloors());
    }
}
