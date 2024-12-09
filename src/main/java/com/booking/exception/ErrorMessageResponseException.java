package com.booking.exception;

import com.booking.container.ContentReplacerItem;
import com.booking.contract.ErrorContainerForm;
import com.booking.contract.HtmlPageInterface;
import com.booking.services.html.HtmlRenderService;
import lombok.Getter;

@Getter
public class ErrorMessageResponseException extends Exception {
    private final HtmlPageInterface htmlPageInterface;

    public ErrorMessageResponseException(String message, HtmlPageInterface htmlPageInterface) {
        super(message);

        if (htmlPageInterface instanceof ErrorContainerForm) {
            ((ErrorContainerForm) htmlPageInterface).addAdditionalContentReplacerItem(
                new ContentReplacerItem(HtmlRenderService.ERROR_MESSAGE_INDEX, message)
            );
        }

        this.htmlPageInterface = htmlPageInterface;
    }

}
