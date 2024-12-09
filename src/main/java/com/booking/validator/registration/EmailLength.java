package com.booking.validator.registration;

import com.booking.contract.RegistrationValidatorInterface;
import com.booking.request.RegistrationFormRequest;

public class EmailLength implements RegistrationValidatorInterface {
    public static final int MAX_LENGTH = 120;

    @Override
    public boolean isValid(RegistrationFormRequest registrationFormRequest) {
        return registrationFormRequest.email().length() <= MAX_LENGTH;
    }

    @Override
    public String getErrorMessage() {
        return String.format("user name should be less than %d", MAX_LENGTH);
    }
}
