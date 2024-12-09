package com.booking.form;

import com.booking.collection.ContentReplacerItemCollection;
import com.booking.container.ContentReplacerItem;
import com.booking.contract.HtmlPageInterface;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginForm extends AbstractErrorForm implements HtmlPageInterface {
    private final String email;
    private final String password;

    @Override
    public String getHtmlFileLocation() {
        return "templates/authorization/login.html";
    }

    @Override
    public String getPageTitle() {
        return "login page";
    }

    @Override
    public ContentReplacerItemCollection getReplacerItems() {
        ContentReplacerItemCollection replacerItems = new ContentReplacerItemCollection();

        replacerItems.addContentReplacerItem(new ContentReplacerItem("email", this.email));
        replacerItems.addContentReplacerItem(new ContentReplacerItem("password", this.password));

        this.addAdditionalItems(replacerItems);

        return replacerItems;
    }
}
