package com.booking.services.hotel;

import com.booking.db.entity.AppUser;
import com.booking.db.entity.Hotel;
import com.booking.db.repository.HotelRepository;
import com.booking.dto.SuccessResponseDTO;
import com.booking.exception.JsonErrorException;
import com.booking.services.SessionMessageService;
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

    public SuccessResponseDTO addHotel(
        HttpServletRequest req,
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
}
