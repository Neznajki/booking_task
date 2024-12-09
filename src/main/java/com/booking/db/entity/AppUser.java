package com.booking.db.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.Collection;
import java.util.Collections;

@Entity
@Getter
public class AppUser implements UserDetails {
    @Id
    @Getter
    @GeneratedValue
    private Integer id;
    @Setter
    private String name;
    @Setter
    private String email;
    @Setter
    private String password;
    @Setter
    private boolean confirmed;

    public AppUser() {}

    public AppUser(
        String name,
        String email,
        String password
    ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.confirmed = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");
        return Collections.singleton(authority);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.confirmed;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.confirmed;
    }
}
