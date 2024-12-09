package com.booking.handler;

import com.booking.exception.ErrorMessageResponseException;
import com.booking.exception.RedirectException;
import com.booking.services.html.HtmlRenderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.view.RedirectView;

@ControllerAdvice
public class CustomErrorHandler extends ResponseEntityExceptionHandler {
    private final HtmlRenderService htmlRenderService;

    @Autowired
    public CustomErrorHandler(HtmlRenderService htmlRenderService) {
        this.htmlRenderService = htmlRenderService;
    }

    @ExceptionHandler({ ErrorMessageResponseException.class })
    public ResponseEntity<String> handleException(HttpServletRequest req, ErrorMessageResponseException exception) {
        return ResponseEntity.badRequest().body(
            htmlRenderService.createResponse(exception.getHtmlPageInterface(), req)
        );
    }

    @ExceptionHandler({ RedirectException.class })
    public RedirectView handleException(RedirectException exception) {
        return new RedirectView(exception.getRedirectRelativePath());
    }
}
