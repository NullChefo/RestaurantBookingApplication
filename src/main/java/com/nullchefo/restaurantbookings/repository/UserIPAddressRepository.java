package com.nullchefo.restaurantbookings.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.nullchefo.restaurantbookings.entity.User;
import com.nullchefo.restaurantbookings.entity.UserIPAddress;

import jdk.jfr.Registered;

@Registered
public interface UserIPAddressRepository
		extends JpaRepository<UserIPAddress, UUID>, JpaSpecificationExecutor<UserIPAddress> {
	List<UserIPAddress> findAllByUser(User user);
}
