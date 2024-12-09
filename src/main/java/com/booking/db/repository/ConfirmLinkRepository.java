package com.booking.db.repository;

import com.booking.db.entity.ConfirmLink;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfirmLinkRepository extends CrudRepository<ConfirmLink, Long> {
    List<ConfirmLink> findByUserEmail(String email);
}
