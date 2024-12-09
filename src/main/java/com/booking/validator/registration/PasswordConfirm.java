package com.booking.validator.registration;

import com.booking.contract.RegistrationValidatorInterface;
import com.booking.request.RegistrationFormRequest;

public class PasswordConfirm implements RegistrationValidatorInterface {
    @Override
    public boolean isValid(RegistrationFormRequest registrationFormRequest) {
        return registrationFormRequest.password().equals(
            registrationFormRequest.passwordRepeat()
        );
    }

    @Override
    public String getErrorMessage() {
        return "password and password repeat should be match";
    }
}
