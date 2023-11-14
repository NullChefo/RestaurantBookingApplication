package com.nullchefo.restaurantbookings.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nullchefo.restaurantbookings.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
