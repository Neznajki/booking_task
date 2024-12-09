package com.booking.db.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Hotel {
    public Hotel() {
    }

    public Hotel(AppUser user, String name, String address, Integer floors) {
        this.user = user;
        this.name = name;
        this.address = address;
        this.floors = floors;
    }

    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;
    private String name;
    private String address;
    private Integer floors;
}
