package com.booking.services.hotel.room;

import com.booking.db.entity.Hotel;
import com.booking.db.entity.HotelRoom;
import com.booking.db.repository.HotelRoomRepository;
import com.booking.db.repository.HotelRoomReservationRepository;
import com.booking.dto.HotelRoomDTO;
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
public class HotelRoomService {
    private final HotelRoomRepository hotelRoomRepository;
    private final HotelRoomReservationRepository hotelRoomReservationRepository;
    private final HotelRoomListGeneratorService hotelRoomListGeneratorService;
    private final SessionMessageService sessionMessageService;

    public String getListContent(Hotel hotel) {
        return hotelRoomListGeneratorService.getListContent(hotelRoomRepository.findByHotel(hotel));
    }

    public SuccessResponseDTO add(
        HotelRoomDTO hotelRoomDTO
    ) throws JsonErrorException {
        String name = hotelRoomDTO.getName();
        Optional<HotelRoom> duplicate = hotelRoomRepository.findByHotelAndName(hotelRoomDTO.getHotel(), name);

        if (duplicate.isPresent()) {
            throw new JsonErrorException(String.format("Room with name %s already exists", name));
        }

        HotelRoom hotelRoom = new HotelRoom(
            hotelRoomDTO.getHotel(),
            name,
            hotelRoomDTO.getFloor(),
            hotelRoomDTO.getMaxPersons()
        );

        hotelRoomRepository.save(hotelRoom);
        return new SuccessResponseDTO(String.format("Room %s Added", name));
    }

    public RedirectView delete(HttpServletRequest req, HotelRoom hotelRoom) {
        if (hotelRoomReservationRepository.findByRoom(hotelRoom).isEmpty()) {
            hotelRoomRepository.delete(hotelRoom);
            sessionMessageService.rememberMessage(
                    req.getSession(),
                    HtmlRenderService.SUCCESS_MESSAGE_INDEX,
                    String.format("hotelRoom with name %s deleted", hotelRoom.getName())
            );
        } else {
            sessionMessageService.rememberMessage(
                    req.getSession(),
                    HtmlRenderService.ERROR_MESSAGE_INDEX,
                    "please delete all rooms before delete"
            );
        }

        return new RedirectView(String.format("/private/hotel/room/%d/list", hotelRoom.getHotel().getId()));
    }
}
