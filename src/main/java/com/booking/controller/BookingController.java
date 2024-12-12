package com.booking.controller;

import com.booking.db.entity.AppUser;
import com.booking.db.entity.HotelRoom;
import com.booking.db.entity.HotelRoomReservation;
import com.booking.dto.DatePickedDTO;
import com.booking.dto.DateSelectionDTO;
import com.booking.dto.SuccessRefreshResponse;
import com.booking.exception.JsonErrorException;
import com.booking.form.BookingSelectionForm;
import com.booking.services.UserService;
import com.booking.services.hotel.room.BookingService;
import com.booking.services.html.HtmlRenderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/private/booking")
public class BookingController {
    private final HtmlRenderService htmlRenderService;
    private final BookingService bookingService;
    private final UserService userService;

    @GetMapping("")
    public String listBooking(@AuthenticationPrincipal AppUser userDetails, HttpServletRequest req) {
        return this.htmlRenderService.createResponse(
                new BookingSelectionForm(userDetails, null, null),
                req
        );
    }

    @GetMapping("/search")
    public String listBooking(@AuthenticationPrincipal AppUser userDetails, HttpServletRequest req, DateSelectionDTO dateSelectionDTO) {
        return this.htmlRenderService.createResponse(
                new BookingSelectionForm(userDetails, dateSelectionDTO, bookingService.getListContent(dateSelectionDTO)),
                req
        );
    }

    @PostMapping(value = "/book/date", produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessRefreshResponse bookDate(
        @AuthenticationPrincipal AppUser userDetails,
        HttpServletRequest req,
        DatePickedDTO datePickedDTO,
        HotelRoom room
    ) {
        return bookingService.bookDates(req, userDetails, datePickedDTO);
    }

    @PostMapping(value = "/delete/{booking}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessRefreshResponse delete(
            @AuthenticationPrincipal AppUser userDetails,
            HttpServletRequest req,
            HotelRoomReservation booking
    ) throws JsonErrorException {
        userService.checkDeletePermissions(userDetails, booking);
        return bookingService.delete(req, booking);
    }
}
