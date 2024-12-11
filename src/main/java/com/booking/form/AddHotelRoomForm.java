package com.booking.form;

import com.booking.collection.ContentReplacerItemCollection;
import com.booking.container.ContentReplacerItem;
import com.booking.contract.HtmlPageInterface;
import com.booking.db.entity.AppUser;
import com.booking.dto.HotelRoomDTO;

public class AddHotelRoomForm implements HtmlPageInterface {
    private final AppUser appUser;
    private final HotelRoomDTO hotelRoomDTO;

    public AddHotelRoomForm(AppUser appUser, HotelRoomDTO hotelRoomDTO) {
        this.appUser = appUser;
        this.hotelRoomDTO = hotelRoomDTO;
    }

    @Override
    public String getHtmlFileLocation() {
        return "templates/hotel/room/addRoomItem.html";
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

        replacerItems.addContentReplacerItem(new ContentReplacerItem("roomName", hotelRoomDTO.getName()));
        replacerItems.addContentReplacerItem(new ContentReplacerItem("maxPersons", String.valueOf(hotelRoomDTO.getMaxPersons())));
        replacerItems.addContentReplacerItem(new ContentReplacerItem("floor", String.valueOf(hotelRoomDTO.getFloor())));

        replacerItems.addContentReplacerItem(new ContentReplacerItem("hotelId", hotelRoomDTO.getHotel().getId().toString()));
        replacerItems.addContentReplacerItem(new ContentReplacerItem("address", hotelRoomDTO.getHotel().getAddress()));
        replacerItems.addContentReplacerItem(new ContentReplacerItem("hotelName", hotelRoomDTO.getHotel().getName()));

        return replacerItems;
    }
}
