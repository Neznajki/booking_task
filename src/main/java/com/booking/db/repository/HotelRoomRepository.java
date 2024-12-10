package com.booking.db.repository;

import com.booking.db.entity.Hotel;
import com.booking.db.entity.HotelRoom;
import com.booking.db.repository.customRepo.RefreshableRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRoomRepository extends RefreshableRepository<HotelRoom, Long> {
    List<HotelRoom> findByHotel(Hotel hotel);
}
