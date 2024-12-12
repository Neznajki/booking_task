package com.booking.services.hotel.room;

import com.booking.db.entity.AppUser;
import com.booking.db.entity.HotelRoomReservation;
import com.booking.db.repository.HotelRoomRepository;
import com.booking.db.repository.HotelRoomReservationRepository;
import com.booking.dto.DatePickedDTO;
import com.booking.dto.DateSelectionDTO;
import com.booking.dto.SuccessRefreshResponse;
import com.booking.services.SessionMessageService;
import com.booking.services.html.HtmlRenderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final HotelRoomReservationRepository hotelRoomReservationRepository;
    private final HotelRoomRepository hotelRoomRepository;
    private final BookingListGenerationService bookingListGenerationService;
    private final SessionMessageService sessionMessageService;

    public String getListContent(DateSelectionDTO dateSelectionDTO) {
        return bookingListGenerationService.getListContent(hotelRoomRepository.findFreeRooms(dateSelectionDTO.getArrivalDate(), dateSelectionDTO.getLeavingDate(), dateSelectionDTO.getPersons()), dateSelectionDTO);
    }

    /**
     * comments are bad but, we could split Date into Date Time and set leaving date 12:00 and arrival date 15:00
     * this requires more efforts with unique keys and field with null value to reset unique key. in this case everything should work fine with same logic.
     */
    @Transactional
    public SuccessRefreshResponse bookDates(HttpServletRequest req, AppUser userDetails, @Validated DatePickedDTO datePickedDTO) {
        List<HotelRoomReservation> hotelRoomReservations = new ArrayList<>();
        Calendar currentDay = Calendar.getInstance();
        currentDay.setTime(datePickedDTO.getArrivalDate());
        while (datePickedDTO.getLeavingDate().after(currentDay.getTime()) || DateUtils.isSameDay(currentDay.getTime(), datePickedDTO.getLeavingDate())) {
            currentDay.add(Calendar.DATE, 1);
            HotelRoomReservation hotelRoomReservation = new HotelRoomReservation(
                    userDetails,
                    datePickedDTO.getRoom(),
                    currentDay.getTime()
            );
            hotelRoomReservations.add(hotelRoomReservation);
        }

        hotelRoomReservationRepository.saveAll(hotelRoomReservations);
        sessionMessageService.rememberMessage(
                req.getSession(),
                HtmlRenderService.SUCCESS_MESSAGE_INDEX,
                "Hotel room booked"
        );
        return new SuccessRefreshResponse();
    }

    public SuccessRefreshResponse delete(HttpServletRequest req, HotelRoomReservation hotelRoomReservation) {
        hotelRoomReservationRepository.delete(hotelRoomReservation);
        sessionMessageService.rememberMessage(
            req.getSession(),
            HtmlRenderService.SUCCESS_MESSAGE_INDEX,
            String.format(
                "hotelRoomReservation with name %s at %s located %s deleted",
                hotelRoomReservation.getRoom().getName(),
                hotelRoomReservation.getRoom().getHotel().getName(),
                hotelRoomReservation.getRoom().getHotel().getAddress()
            )
        );

        return new SuccessRefreshResponse();
    }
}
