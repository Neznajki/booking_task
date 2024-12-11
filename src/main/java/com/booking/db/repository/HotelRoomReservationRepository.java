package com.booking.db.repository;

import com.booking.db.entity.HotelRoom;
import com.booking.db.entity.HotelRoomReservation;
import com.booking.db.repository.customRepo.RefreshableRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRoomReservationRepository extends RefreshableRepository<HotelRoomReservation, Integer> {
    List<HotelRoomReservation> findByRoom(HotelRoom hotelRoom);
}
