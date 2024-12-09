package com.booking.config;

import com.booking.factory.RedirectFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CustomHttp403ForbiddenEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        final String s = RedirectFactory.getLoginUrl().getUrl();
        response.sendRedirect(s);
    }
}
