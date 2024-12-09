package com.booking.db.repository;

import com.booking.db.entity.Ip;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IpRepository extends CrudRepository<Ip, Long> {
    Optional<Ip> findByIpAddress(String ipAddress);
}
