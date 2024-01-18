package com.nullchefo.restaurantbookings.service;

import com.nullchefo.restaurantbookings.entity.UserVaadin;
import com.nullchefo.restaurantbookings.repository.UserRepositoryVaadin;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class UserServiceVaadin {

    private final UserRepositoryVaadin repository;

    public UserServiceVaadin(UserRepositoryVaadin repository) {
        this.repository = repository;
    }

    public Optional<UserVaadin> get(Long id) {
        return repository.findById(id);
    }

    public UserVaadin update(UserVaadin entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<UserVaadin> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<UserVaadin> list(Pageable pageable, Specification<UserVaadin> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
