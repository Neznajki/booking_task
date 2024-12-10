package com.booking.handler;

import com.booking.dto.ErrorResponseDTO;
import com.booking.exception.ErrorMessageResponseException;
import com.booking.exception.JsonErrorException;
import com.booking.exception.RedirectException;
import com.booking.services.html.HtmlRenderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
public class CustomErrorHandler extends ResponseEntityExceptionHandler {
    private final HtmlRenderService htmlRenderService;
    private final MessageSource messageSource;

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

    @ExceptionHandler({ JsonErrorException.class })
    public ResponseEntity<ErrorResponseDTO> handleException(JsonErrorException exception) {
        return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        String errorMessage = errors.keySet().stream()
                .map(key -> key + "=" + messageSource.getMessage(errors.get(key), null, errors.get(key), request.getLocale()))
                .collect(Collectors.joining(", ", "{", "}"));
        return ResponseEntity.badRequest().body(new ErrorResponseDTO(errorMessage));
    }
}
