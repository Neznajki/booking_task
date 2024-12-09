package com.booking.form;

import com.booking.collection.ContentReplacerItemCollection;
import com.booking.container.ContentReplacerItem;
import com.booking.contract.HtmlPageInterface;
import com.booking.db.entity.AppUser;
import com.booking.services.html.HtmlRenderService;

public class ListHotelForm implements HtmlPageInterface {
    private final AppUser appUser;
    private final String successMessage;
    private final String listData;

    public ListHotelForm(AppUser appUser, String successMessage, String listData) {
        this.appUser = appUser;
        this.successMessage = successMessage;
        this.listData = listData;
    }

    @Override
    public String getHtmlFileLocation() {
        return "templates/hotel/listPreview.html";
    }

    @Override
    public String getPageTitle() {
        return "Hotel List";
    }

    @Override
    public ContentReplacerItemCollection getReplacerItems() {
        ContentReplacerItemCollection replacerItems = new ContentReplacerItemCollection();

        replacerItems.addContentReplacerItem(new ContentReplacerItem("email", appUser.getEmail()));
        replacerItems.addContentReplacerItem(new ContentReplacerItem("name", appUser.getName()));
        replacerItems.addContentReplacerItem(new ContentReplacerItem("listData", listData));
        replacerItems.addContentReplacerItem(new ContentReplacerItem(HtmlRenderService.SUCCESS_MESSAGE_INDEX, successMessage));

        return replacerItems;
    }
}