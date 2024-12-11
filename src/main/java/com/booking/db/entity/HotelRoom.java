package com.booking.db.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class HotelRoom {
    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    private String name;
    private Integer floor;
    private Integer maxPersons;

    public HotelRoom() {
    }

    public HotelRoom(Hotel hotel, String name, Integer floor, Integer maxPersons) {
        this.hotel = hotel;
        this.name = name;
        this.floor = floor;
        this.maxPersons = maxPersons;
    }
}
