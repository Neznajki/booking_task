package com.booking.controller;

import com.booking.db.entity.AppUser;
import com.booking.form.PrivateCabinetForm;
import com.booking.services.SessionMessageService;
import com.booking.services.html.HtmlRenderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/private")
public class CabinetController {
    private final HtmlRenderService htmlRenderService;
    private final SessionMessageService sessionMessageService;

    @GetMapping("/cabinet")
    public String privateCabinet(@AuthenticationPrincipal AppUser userDetails, HttpServletRequest req) {
        return this.htmlRenderService.createResponse(
                new PrivateCabinetForm(userDetails, sessionMessageService.getMessageForDisplay(req.getSession(), "successMessage")),
                req
        );
    }
}
