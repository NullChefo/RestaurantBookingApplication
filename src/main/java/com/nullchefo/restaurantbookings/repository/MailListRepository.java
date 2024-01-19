package com.nullchefo.restaurantbookings.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.nullchefo.restaurantbookings.entity.MailList;
import com.nullchefo.restaurantbookings.entity.User;

public interface MailListRepository extends JpaRepository<MailList, Long>, JpaSpecificationExecutor<MailList> {
	MailList findByUser(User user);
}
