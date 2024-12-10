package com.booking.controller;

import com.booking.db.entity.AppUser;
import com.booking.db.entity.Hotel;
import com.booking.dto.HotelDTO;
import com.booking.dto.SuccessResponseDTO;
import com.booking.exception.JsonErrorException;
import com.booking.form.AddHotelForm;
import com.booking.form.ListHotelForm;
import com.booking.services.SessionMessageService;
import com.booking.services.hotel.HotelService;
import com.booking.services.html.HtmlRenderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequiredArgsConstructor
@RequestMapping("/private/hotel")
public class HotelController {
    private final HtmlRenderService htmlRenderService;
    private final HotelService hotelService;
    private final SessionMessageService sessionMessageService;

    @GetMapping("/list")
    public String list(@AuthenticationPrincipal AppUser userDetails, HttpServletRequest req) {
        return this.htmlRenderService.createResponse(
                new ListHotelForm(
                    userDetails,
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
        return this.hotelService.add(userDetails, hotelDTO.getName(), hotelDTO.getAddress(), hotelDTO.getFloors());
    }

    @PostMapping(value = "/delete/{hotel}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RedirectView delete(
            @AuthenticationPrincipal AppUser userDetails,
            Hotel hotel,
            HttpServletRequest req
    ) {
        return this.hotelService.deleteHotel(req, hotel);
    }
}
