package com.booking.exception;

import lombok.Getter;

@Getter
public class RedirectException extends Exception {
    private final String redirectRelativePath;

    public RedirectException(String redirectRelativePath) {
        super("Redirect exception should redirect !!");
        this.redirectRelativePath = redirectRelativePath;
    }

}
