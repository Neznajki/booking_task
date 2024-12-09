package com.booking.factory;

import org.springframework.web.servlet.view.RedirectView;

public class RedirectFactory {
    public static RedirectView getCabinetUrl() {
        return new RedirectView("/private/cabinet");
    }

    public static RedirectView getLoginUrl() {
        return new RedirectView("/login");
    }
}
