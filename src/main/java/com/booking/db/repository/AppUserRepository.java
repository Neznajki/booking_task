package com.booking.db.repository;

import com.booking.db.entity.AppUser;
import com.booking.db.repository.customRepo.RefreshableRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends RefreshableRepository<AppUser, Integer> {
    Optional<AppUser> findByEmail(String email);
}