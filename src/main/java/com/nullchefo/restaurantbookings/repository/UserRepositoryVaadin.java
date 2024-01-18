package com.nullchefo.restaurantbookings.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.nullchefo.restaurantbookings.entity.UserVaadin;

public interface UserRepositoryVaadin extends JpaRepository<UserVaadin, Long>, JpaSpecificationExecutor<UserVaadin> {

    UserVaadin findByUsername(String username);
}
