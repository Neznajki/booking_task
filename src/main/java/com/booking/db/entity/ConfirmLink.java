package com.booking.db.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class ConfirmLink {
    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;
    @Column(nullable = false)
    private String confirmHash;

    public ConfirmLink() {
    }

    public ConfirmLink(AppUser user, String confirmHash) {
        this.user = user;
        this.confirmHash = confirmHash;
    }
}
