package com.nullchefo.restaurantbookings.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nullchefo.restaurantbookings.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
	Optional<User> findByEmail(String email);

	Optional<User> findByEmailIgnoreCase(String email);

	User findByEmailIgnoreCaseAndDeleted(String email, boolean deleted);

	User findByUsername(String username);
}
