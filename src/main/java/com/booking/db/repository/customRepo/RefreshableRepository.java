package com.booking.db.repository.customRepo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface RefreshableRepository<T, ID> extends CrudRepository<T, ID> {
    void refresh(T t);
}
