package com.nullchefo.restaurantbookings.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.nullchefo.restaurantbookings.entity.User;
import com.nullchefo.restaurantbookings.repository.UserRepository;

@Service
public class UserService extends BaseService<User> {


	private final UserRepository userRepository;

	public UserService(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	protected JpaRepository<User, UUID> getRepo() {
		return this.userRepository;
	}

}
