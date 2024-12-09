package com.booking.services.hotel;

import com.booking.db.entity.AppUser;
import com.booking.db.entity.Hotel;
import com.booking.db.repository.HotelRepository;
import com.booking.services.SessionMessageService;
import com.booking.services.html.HtmlRenderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;
    private final HotelListGeneratorService hotelListGeneratorService;
    private final SessionMessageService sessionMessageService;

    public String getListContent(AppUser user) {
        return hotelListGeneratorService.getListContent(hotelRepository.findByUser(user));
    }

    public void addHotel(
        HttpServletRequest req,
        AppUser user,
        String name,
        String address,
        Integer floors
    ) {
        Optional<Hotel> duplicate = hotelRepository.findByName(name);

        if (duplicate.isPresent()) {
            sessionMessageService.rememberMessage(req.getSession(), HtmlRenderService.ERROR_MESSAGE_INDEX, "Duplicate Hotel");
            return;
        }

        Hotel hotel = new Hotel(
            user,
            name,
            address,
            floors
        );

        hotelRepository.save(hotel);
        sessionMessageService.rememberMessage(req.getSession(), HtmlRenderService.SUCCESS_MESSAGE_INDEX, String.format("Hotel %s Added", name));
    }
}
