package com.booking.validator.registration;

import com.booking.contract.RegistrationValidatorInterface;
import com.booking.request.RegistrationFormRequest;

import java.util.regex.Pattern;

public class EmailSymbolMatch implements RegistrationValidatorInterface {
    @Override
    public boolean isValid(RegistrationFormRequest registrationFormRequest) {
        return Pattern
            .compile("^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$")
            .matcher(registrationFormRequest.email())
            .matches();
    }

    @Override
    public String getErrorMessage() {
        return "email should be email (email@domain.com)";
    }
}
