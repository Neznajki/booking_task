package com.booking.form;

import com.booking.collection.ContentReplacerItemCollection;
import com.booking.container.ContentReplacerItem;
import com.booking.contract.HtmlPageInterface;
import com.booking.db.entity.AppUser;
import com.booking.dto.DateSelectionDTO;
import com.booking.helper.DateTimeHelper;

public class BookingSelectionForm implements HtmlPageInterface {
    private final AppUser appUser;
    private final DateSelectionDTO dateSelectionDTO;
    private final String listData;

    public BookingSelectionForm(
            AppUser appUser,
            DateSelectionDTO dateSelectionDTO,
            String listData
    ) {
        this.appUser = appUser;
        this.dateSelectionDTO = dateSelectionDTO;
        this.listData = listData;
    }

    @Override
    public String getHtmlFileLocation() {
        return "templates/hotel/room/reservation/listPreview.html";
    }

    @Override
    public String getPageTitle() {
        return "Booking selection";
    }

    @Override
    public ContentReplacerItemCollection getReplacerItems() {
        ContentReplacerItemCollection replacerItems = new ContentReplacerItemCollection();

        replacerItems.addContentReplacerItem(new ContentReplacerItem("email", appUser.getEmail()));
        replacerItems.addContentReplacerItem(new ContentReplacerItem("name", appUser.getName()));
        replacerItems.addContentReplacerItem(new ContentReplacerItem("arrivalDate", DateTimeHelper.formatDate(dateSelectionDTO.getArrivalDate())));
        replacerItems.addContentReplacerItem(new ContentReplacerItem("leavingDate", DateTimeHelper.formatDate(dateSelectionDTO.getLeavingDate())));
        replacerItems.addContentReplacerItem(new ContentReplacerItem("persons", dateSelectionDTO.getPersons().toString()));
        replacerItems.addContentReplacerItem(new ContentReplacerItem("listData", listData == null || listData.isEmpty() ? "No available rooms please select other dates" : listData));

        return replacerItems;
    }
}
