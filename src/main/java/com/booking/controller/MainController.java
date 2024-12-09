package com.booking.controller;

import com.booking.exception.ErrorMessageResponseException;
import com.booking.factory.RedirectFactory;
import com.booking.form.LoginForm;
import com.booking.form.RegistrationForm;
import com.booking.request.LoginFormRequest;
import com.booking.request.RegistrationFormRequest;
import com.booking.services.ConfirmLinkService;
import com.booking.services.LoginService;
import com.booking.services.RegisterService;
import com.booking.services.html.HtmlRenderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequiredArgsConstructor
public class MainController {
    private final HtmlRenderService htmlRenderService;
    private final LoginService loginService;
    private final RegisterService registerService;
    private final ConfirmLinkService confirmLinkService;

    @GetMapping("/login")
    public String login(HttpServletRequest req, String email) {
        if (email == null) {
            email = "";
        }

        return htmlRenderService.createResponse(
            new LoginForm(email, ""),
            req
        );
    }

    @PostMapping("/login")
    public RedirectView login(HttpServletRequest req, LoginFormRequest loginForm) throws ErrorMessageResponseException {
        loginService.checkLoginCredentials(req, loginForm);
        return RedirectFactory.getCabinetUrl();
    }

    @GetMapping("/register")
    public String register(HttpServletRequest req) {
        return htmlRenderService.createResponse(
            new RegistrationForm("", "", "", ""), req
        );
    }

    @PostMapping("/register")
    public String register(HttpServletRequest req, RegistrationFormRequest registrationFormRequest) throws ErrorMessageResponseException {
        return htmlRenderService.createResponse(
            registerService.doRegister(registrationFormRequest), req
        );
    }

    @GetMapping("/confirm/hash")
    public RedirectView confirmHash(
        HttpServletRequest req,
        RedirectAttributes redirAttrs,
        String email,
        String hash
    ) {
        if (confirmLinkService.confirmUserEmail(req, email, hash)) {
            redirAttrs.addAttribute("email", email);
        }

        return RedirectFactory.getLoginUrl();
    }


}
