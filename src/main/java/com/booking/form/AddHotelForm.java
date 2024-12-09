package com.booking.form;

import com.booking.collection.ContentReplacerItemCollection;
import com.booking.container.ContentReplacerItem;
import com.booking.contract.HtmlPageInterface;
import com.booking.db.entity.AppUser;
import com.booking.dto.HotelDTO;

public class AddHotelForm implements HtmlPageInterface {
    private final AppUser appUser;
    private final HotelDTO hotelDTO;

    public AddHotelForm(AppUser appUser, HotelDTO hotelDTO) {
        this.appUser = appUser;
        this.hotelDTO = hotelDTO;
    }

    @Override
    public String getHtmlFileLocation() {
        return "templates/hotel/addHotelItem.html";
    }

    @Override
    public String getPageTitle() {
        return "Hotel Add";
    }

    @Override
    public ContentReplacerItemCollection getReplacerItems() {
        ContentReplacerItemCollection replacerItems = new ContentReplacerItemCollection();

        replacerItems.addContentReplacerItem(new ContentReplacerItem("email", appUser.getEmail()));
        replacerItems.addContentReplacerItem(new ContentReplacerItem("name", appUser.getName()));
        replacerItems.addContentReplacerItem(new ContentReplacerItem("hotel_name", hotelDTO.getName()));
        replacerItems.addContentReplacerItem(new ContentReplacerItem("address", hotelDTO.getAddress()));
        replacerItems.addContentReplacerItem(new ContentReplacerItem("floors", String.valueOf(hotelDTO.getFloors())));

        return replacerItems;
    }
}
