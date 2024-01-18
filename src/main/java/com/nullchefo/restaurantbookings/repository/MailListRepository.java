package com.nullchefo.restaurantbookings.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nullchefo.restaurantbookings.entity.MailList;
import com.nullchefo.restaurantbookings.entity.User;

public interface MailListRepository extends JpaRepository<MailList, Long> {
    MailList findByUser(User user);
}
