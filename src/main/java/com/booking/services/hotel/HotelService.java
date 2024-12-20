package com.booking.services.hotel;

import com.booking.db.entity.AppUser;
import com.booking.db.entity.Hotel;
import com.booking.db.repository.HotelRepository;
import com.booking.db.repository.HotelRoomRepository;
import com.booking.dto.SuccessResponseDTO;
import com.booking.exception.JsonErrorException;
import com.booking.services.SessionMessageService;
import com.booking.services.html.HtmlRenderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;
    private final HotelRoomRepository hotelRoomRepository;
    private final HotelListGeneratorService hotelListGeneratorService;
    private final SessionMessageService sessionMessageService;

    public String getListContent(AppUser user) {
        return hotelListGeneratorService.getListContent(hotelRepository.findByUser(user));
    }

    public SuccessResponseDTO add(
        AppUser user,
        String name,
        String address,
        Integer floors
    ) throws JsonErrorException {
        Optional<Hotel> duplicate = hotelRepository.findByName(name);

        if (duplicate.isPresent()) {
            throw new JsonErrorException(String.format("Hotel with name %s already exists", name));
        }

        Hotel hotel = new Hotel(
            user,
            name,
            address,
            floors
        );

        hotelRepository.save(hotel);
        return new SuccessResponseDTO(String.format("Hotel %s Added", name));
    }

    public RedirectView deleteHotel(HttpServletRequest req, Hotel hotel) {
        if (hotelRoomRepository.findByHotel(hotel).isEmpty()) {
            hotelRepository.delete(hotel);
            sessionMessageService.rememberMessage(
                req.getSession(),
                HtmlRenderService.SUCCESS_MESSAGE_INDEX,
                String.format("hotel with name %s deleted", hotel.getName())
            );
        } else {
            sessionMessageService.rememberMessage(
                    req.getSession(),
                    HtmlRenderService.ERROR_MESSAGE_INDEX,
                    "please delete all rooms before delete"
            );
        }

        return new RedirectView("/private/hotel/list");
    }
}
