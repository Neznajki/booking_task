package com.booking.services.hotel.room;

import com.booking.collection.ContentReplacerItemCollection;
import com.booking.container.ContentReplacerItem;
import com.booking.db.entity.HotelRoom;
import com.booking.services.html.HtmlReaderService;
import com.booking.services.html.HtmlReplacerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelRoomListGeneratorService {
    private final HtmlReaderService htmlReaderService;
    private final HtmlReplacerService htmlReplacerService;

    @Autowired
    public HotelRoomListGeneratorService(HtmlReaderService htmlReaderService, HtmlReplacerService htmlReplacerService) {
        this.htmlReaderService = htmlReaderService;
        this.htmlReplacerService = htmlReplacerService;
    }

    public String getListContent(List<HotelRoom> hotelRoomList) {
        StringBuilder result = new StringBuilder();
        for (HotelRoom hotelRoom: hotelRoomList) {
            result.append(renderSingleItem(hotelRoom));
        }
        return result.toString();
    }

    private String renderSingleItem(HotelRoom hotelRoom) {
        String htmlContent = htmlReaderService.readHtmlFileContent("templates/hotel/room/listIem.html");
        ContentReplacerItemCollection replacerItemCollection = new ContentReplacerItemCollection();

        replacerItemCollection.addContentReplacerItem(new ContentReplacerItem("id", hotelRoom.getId().toString()));
        replacerItemCollection.addContentReplacerItem(new ContentReplacerItem("name", hotelRoom.getName()));
        replacerItemCollection.addContentReplacerItem(new ContentReplacerItem("floor", hotelRoom.getFloor().toString()));
        replacerItemCollection.addContentReplacerItem(new ContentReplacerItem("maxPersons", hotelRoom.getMaxPersons().toString()));

        return htmlReplacerService.replaceItems(htmlContent, replacerItemCollection);
    }
}
