package com.booking.validator.registration;

import com.booking.contract.RegistrationValidatorInterface;
import com.booking.request.RegistrationFormRequest;

public class NameMinLength implements RegistrationValidatorInterface {
    public static final int MIN_NAME_LENGTH = 6;

    @Override
    public boolean isValid(RegistrationFormRequest registrationFormRequest) {
        return registrationFormRequest.name().length() >= MIN_NAME_LENGTH;
    }

    @Override
    public String getErrorMessage() {
        return String.format("name should contain at least %d symbols", MIN_NAME_LENGTH);
    }
}
