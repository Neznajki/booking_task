package com.booking.services;

import com.booking.contract.EncodingInterface;
import com.booking.contract.HtmlPageInterface;
import com.booking.contract.RegistrationValidatorInterface;
import com.booking.db.entity.AppUser;
import com.booking.encoder.Sha512Encoder;
import com.booking.exception.DuplicateUserException;
import com.booking.exception.ErrorMessageResponseException;
import com.booking.form.RegistrationForm;
import com.booking.request.RegistrationFormRequest;
import com.booking.validator.registration.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegisterService {
    private final EncodingInterface passwordEncoder = new Sha512Encoder();
    private final UserService userService;
    private final ConfirmLinkService confirmLinkService;
    private final List<RegistrationValidatorInterface> validations = new ArrayList<>();

    @Autowired
    public RegisterService(UserService userService, ConfirmLinkService confirmLinkService) {
        this.userService = userService;
        this.confirmLinkService = confirmLinkService;

        validations.add(new EmailLength());
        validations.add(new EmailSymbolMatch());
        validations.add(new NameLength());
        validations.add(new NameMinLength());
        validations.add(new PasswordConfirm());
        validations.add(new PasswordMinLength());
    }

    @Transactional
    public HtmlPageInterface doRegister(RegistrationFormRequest registrationFormRequest) throws ErrorMessageResponseException {
        RegistrationForm resultForm = new RegistrationForm(registrationFormRequest);

        for (RegistrationValidatorInterface validation : validations) {
            if (! validation.isValid(registrationFormRequest)) {
                throw new ErrorMessageResponseException(
                    validation.getErrorMessage(),
                    resultForm
                );
            }
        }

        AppUser user;
        try {
            user = userService.createUser(
                registrationFormRequest.email(),
                registrationFormRequest.name(),
                passwordEncoder.encodeString(registrationFormRequest.password())
            );
        } catch (DuplicateUserException e) {
            throw new ErrorMessageResponseException(
                    e.getMessage(),
                    resultForm
            );
        }

        resultForm.setSuccessMessage(
            String.format("follow link to confirm email address <a href=\"%s\">confirm_email</a>", confirmLinkService.createConfirmLink(user))
        );

        return resultForm;
    }
}
