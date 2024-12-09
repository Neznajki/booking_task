package com.booking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/private/booking")
public class BookingController {
//    @GetMapping("/list")
//    public String listBooking(@AuthenticationPrincipal AppUser userDetails, HttpServletRequest req) {
//        return this.htmlRenderService.createResponse(
//                new PrivateCabinetForm(userDetails, sessionMessageService.getMessageForDisplay(req.getSession(), "successMessage")),
//                req
//        );
//    }
}
