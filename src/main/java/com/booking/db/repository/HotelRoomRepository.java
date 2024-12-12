package com.booking.db.repository;

import com.booking.db.entity.Hotel;
import com.booking.db.entity.HotelRoom;
import com.booking.db.repository.customRepo.RefreshableRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRoomRepository extends RefreshableRepository<HotelRoom, Integer> {
    List<HotelRoom> findByHotel(Hotel hotel);
    Optional<HotelRoom> findByHotelAndName(Hotel hotel, String name);
    @Query("""
            SELECT room
            FROM HotelRoom as room
            LEFT JOIN HotelRoomReservation as reservation on reservation.room.id = room.id AND reservation.visitDate >= :arrivalDate AND :leavingDate <= reservation.visitDate
            WHERE room.maxPersons >= :personsCount AND reservation.id IS NULL""")
    List<HotelRoom> findFreeRooms(Date arrivalDate, Date leavingDate, Integer personsCount);
}
