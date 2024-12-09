package com.booking.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class Ip {
    @Id
    @GeneratedValue
    private Integer id;

    @Setter
    private String ipAddress;
}
