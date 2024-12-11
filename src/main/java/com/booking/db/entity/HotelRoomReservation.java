package com.booking.db.entity;

import com.booking.contract.UserDetailsEntityInterface;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Entity
@Getter
public class HotelRoomReservation implements UserDetailsEntityInterface {
    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private HotelRoom room;
    private Date visitDate;

    public HotelRoomReservation() {
    }

    public HotelRoomReservation(AppUser user, HotelRoom room, Date visitDate) {
        this.user = user;
        this.room = room;
        this.visitDate = visitDate;
    }
}
