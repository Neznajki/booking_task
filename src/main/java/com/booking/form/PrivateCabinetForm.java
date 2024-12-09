package com.booking.form;

import com.booking.collection.ContentReplacerItemCollection;
import com.booking.container.ContentReplacerItem;
import com.booking.contract.HtmlPageInterface;
import com.booking.db.entity.AppUser;
import com.booking.services.html.HtmlRenderService;

public class PrivateCabinetForm implements HtmlPageInterface {
    private final AppUser appUser;
    private final String successMessage;

    public PrivateCabinetForm(AppUser appUser, String successMessage) {
        this.appUser = appUser;
        this.successMessage = successMessage;
    }

    @Override
    public String getHtmlFileLocation() {
        return "templates/cabinet/privateCabinet.html";
    }

    @Override
    public String getPageTitle() {
        return "Private Cabinet";
    }

    @Override
    public ContentReplacerItemCollection getReplacerItems() {
        ContentReplacerItemCollection replacerItems = new ContentReplacerItemCollection();

        replacerItems.addContentReplacerItem(new ContentReplacerItem("email", this.appUser.getEmail()));
        replacerItems.addContentReplacerItem(new ContentReplacerItem("name", this.appUser.getName()));
        replacerItems.addContentReplacerItem(new ContentReplacerItem(HtmlRenderService.SUCCESS_MESSAGE_INDEX, this.successMessage));

        return replacerItems;
    }
}
