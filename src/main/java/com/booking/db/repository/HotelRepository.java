package com.booking.db.repository;

import com.booking.db.entity.AppUser;
import com.booking.db.entity.Hotel;
import com.booking.db.repository.customRepo.RefreshableRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends RefreshableRepository<Hotel, Long> {
    List<Hotel> findByUser(AppUser appUser);
    Optional<Hotel> findByName(String name);
}
