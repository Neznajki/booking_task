package com.booking.form;

import com.booking.collection.ContentReplacerItemCollection;
import com.booking.container.ContentReplacerItem;
import com.booking.contract.HtmlPageInterface;
import com.booking.request.RegistrationFormRequest;
import com.booking.services.html.HtmlRenderService;
import lombok.Getter;
import lombok.Setter;

public class RegistrationForm extends AbstractErrorForm implements HtmlPageInterface {
    private final String email;
    private final String name;
    private final String password;
    private final String passwordRepeat;
    @Setter
    @Getter
    private String successMessage = "";

    public RegistrationForm(String email, String name, String password, String passwordRepeat) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.passwordRepeat = passwordRepeat;
    }

    public RegistrationForm(RegistrationFormRequest registrationFormRequest) {
        this.email = registrationFormRequest.email();
        this.name = registrationFormRequest.name();
        this.password = registrationFormRequest.password();
        this.passwordRepeat = registrationFormRequest.passwordRepeat();
    }

    @Override
    public String getHtmlFileLocation() {
        return "templates/authorization/registration.html";
    }

    @Override
    public String getPageTitle() {
        return "login page";
    }

    @Override
    public ContentReplacerItemCollection getReplacerItems() {
        ContentReplacerItemCollection replacerItems = new ContentReplacerItemCollection();

        replacerItems.addContentReplacerItem(new ContentReplacerItem("email", this.email));
        replacerItems.addContentReplacerItem(new ContentReplacerItem("name", this.name));
        replacerItems.addContentReplacerItem(new ContentReplacerItem("password", this.password));
        replacerItems.addContentReplacerItem(new ContentReplacerItem("passwordRepeat", this.passwordRepeat));
        replacerItems.addContentReplacerItem(new ContentReplacerItem(HtmlRenderService.SUCCESS_MESSAGE_INDEX, this.successMessage));

        this.addAdditionalItems(replacerItems);

        return replacerItems;
    }
}
