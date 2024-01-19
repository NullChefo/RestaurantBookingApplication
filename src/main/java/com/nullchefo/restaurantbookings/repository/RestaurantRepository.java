package com.nullchefo.restaurantbookings.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.nullchefo.restaurantbookings.entity.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, UUID>, JpaSpecificationExecutor<Restaurant> {
}
