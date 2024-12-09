package com.booking.controller;

import com.booking.db.entity.AppUser;
import com.booking.dto.HotelDTO;
import com.booking.form.AddHotelForm;
import com.booking.form.ListHotelForm;
import com.booking.services.SessionMessageService;
import com.booking.services.hotel.HotelService;
import com.booking.services.html.HtmlRenderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/private/hotel")
public class HotelController {
    private final HtmlRenderService htmlRenderService;
    private final HotelService hotelService;
    private final SessionMessageService sessionMessageService;

    @GetMapping("/list")
    public String listBooking(@AuthenticationPrincipal AppUser userDetails, HttpServletRequest req) {
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
    public String addBooking(@AuthenticationPrincipal AppUser userDetails, HttpServletRequest req) {
        return this.htmlRenderService.createResponse(new AddHotelForm(userDetails, new HotelDTO()), req);
    }

    @PostMapping("/add")
    public String addBooking(
            @AuthenticationPrincipal AppUser userDetails,
            HttpServletRequest req,
            @Validated HotelDTO hotelDTO
    ) {
        this.hotelService.addHotel(req, userDetails, hotelDTO.getName(), hotelDTO.getAddress(), hotelDTO.getFloors());

        return this.htmlRenderService.createResponse(new AddHotelForm(userDetails, hotelDTO), req);
    }
}
