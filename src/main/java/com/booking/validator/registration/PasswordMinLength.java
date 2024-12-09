package com.booking.validator.registration;

import com.booking.contract.RegistrationValidatorInterface;
import com.booking.request.RegistrationFormRequest;

public class PasswordMinLength implements RegistrationValidatorInterface {
    public static final int MIN_PASSWORD_LENGTH = 6;

    @Override
    public boolean isValid(RegistrationFormRequest registrationFormRequest) {
        return registrationFormRequest.password().length() >= MIN_PASSWORD_LENGTH;
    }

    @Override
    public String getErrorMessage() {
        return String.format("password should contain at least %d symbols", MIN_PASSWORD_LENGTH);
    }
}
