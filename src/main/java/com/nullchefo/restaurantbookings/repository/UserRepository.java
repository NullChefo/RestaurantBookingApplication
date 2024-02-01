package com.nullchefo.restaurantbookings.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nullchefo.restaurantbookings.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
	Optional<User> findByEmail(String email);

	Optional<User> findByEmailOrUsername(String email, String username);

	Optional<User> findByEmailIgnoreCase(String email);

	Optional<User> findByEmailIgnoreCaseAndDeleted(String email, boolean deleted);

	Optional<User> findByUsername(String username);

	Page<User> findAll(Pageable pageable);

	List<User> searchUsersByUsernameIgnoreCase(String username);
}

