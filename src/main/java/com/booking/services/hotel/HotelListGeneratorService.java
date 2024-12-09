package com.booking.services.hotel;

import com.booking.collection.ContentReplacerItemCollection;
import com.booking.container.ContentReplacerItem;
import com.booking.db.entity.Hotel;
import com.booking.services.html.HtmlReaderService;
import com.booking.services.html.HtmlReplacerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelListGeneratorService {
    private final HtmlReaderService htmlReaderService;
    private final HtmlReplacerService htmlReplacerService;

    @Autowired
    public HotelListGeneratorService(HtmlReaderService htmlReaderService, HtmlReplacerService htmlReplacerService) {
        this.htmlReaderService = htmlReaderService;
        this.htmlReplacerService = htmlReplacerService;
    }

    public String getListContent(List<Hotel> hotelList) {
        StringBuilder result = new StringBuilder();
        for (Hotel hotel: hotelList) {
            result.append(renderSingleItem(hotel));
        }
        return result.toString();
    }

    private String renderSingleItem(Hotel hotel) {
        String htmlContent = htmlReaderService.readHtmlFileContent("templates/hotel/hotelListIem.html");
        ContentReplacerItemCollection replacerItemCollection = new ContentReplacerItemCollection();

        replacerItemCollection.addContentReplacerItem(new ContentReplacerItem("id", hotel.getId().toString()));
        replacerItemCollection.addContentReplacerItem(new ContentReplacerItem("name", hotel.getName()));
        replacerItemCollection.addContentReplacerItem(new ContentReplacerItem("floors", hotel.getFloors().toString()));
        replacerItemCollection.addContentReplacerItem(new ContentReplacerItem("address", hotel.getAddress()));
        replacerItemCollection.addContentReplacerItem(new ContentReplacerItem("owner", hotel.getUser().getName()));

        return htmlReplacerService.replaceItems(htmlContent, replacerItemCollection);
    }
}
