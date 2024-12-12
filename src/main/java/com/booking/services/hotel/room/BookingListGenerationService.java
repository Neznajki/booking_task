package com.booking.services.hotel.room;

import com.booking.collection.ContentReplacerItemCollection;
import com.booking.container.ContentReplacerItem;
import com.booking.db.entity.HotelRoom;
import com.booking.dto.DateSelectionDTO;
import com.booking.helper.DateTimeHelper;
import com.booking.services.html.HtmlReaderService;
import com.booking.services.html.HtmlReplacerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingListGenerationService {
    private final HtmlReaderService htmlReaderService;
    private final HtmlReplacerService htmlReplacerService;

    public String getListContent(List<HotelRoom> hotelRoomList, DateSelectionDTO dateSelectionDTO) {
        StringBuilder result = new StringBuilder();
        for (HotelRoom hotelRoom: hotelRoomList) {
            result.append(renderSingleItem(hotelRoom, dateSelectionDTO));
        }
        return result.toString();
    }

    private String renderSingleItem(HotelRoom hotelRoom, DateSelectionDTO dateSelectionDTO) {
        String htmlContent = htmlReaderService.readHtmlFileContent("templates/hotel/room/reservation/listIem.html");
        ContentReplacerItemCollection replacerItemCollection = new ContentReplacerItemCollection();

        replacerItemCollection.addContentReplacerItem(new ContentReplacerItem("id", hotelRoom.getId().toString()));
        replacerItemCollection.addContentReplacerItem(new ContentReplacerItem("name", hotelRoom.getName()));
        replacerItemCollection.addContentReplacerItem(new ContentReplacerItem("floor", hotelRoom.getFloor().toString()));
        replacerItemCollection.addContentReplacerItem(new ContentReplacerItem("maxPersons", hotelRoom.getMaxPersons().toString()));
        replacerItemCollection.addContentReplacerItem(new ContentReplacerItem("arrivalDate", DateTimeHelper.formatDate(dateSelectionDTO.getArrivalDate())));
        replacerItemCollection.addContentReplacerItem(new ContentReplacerItem("leavingDate", DateTimeHelper.formatDate(dateSelectionDTO.getLeavingDate())));
        replacerItemCollection.addContentReplacerItem(new ContentReplacerItem("persons", dateSelectionDTO.getPersons().toString()));


        return htmlReplacerService.replaceItems(htmlContent, replacerItemCollection);
    }
}
