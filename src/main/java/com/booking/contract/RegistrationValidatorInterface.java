package com.booking.contract;

import com.booking.request.RegistrationFormRequest;

public interface RegistrationValidatorInterface {
    boolean isValid(RegistrationFormRequest registrationFormRequest);
    String getErrorMessage();
}
