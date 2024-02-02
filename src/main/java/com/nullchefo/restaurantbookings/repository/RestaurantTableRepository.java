package com.nullchefo.restaurantbookings.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nullchefo.restaurantbookings.entity.Restaurant;
import com.nullchefo.restaurantbookings.entity.RestaurantTable;

@Repository
public interface RestaurantTableRepository
		extends JpaRepository<RestaurantTable, UUID>, JpaSpecificationExecutor<RestaurantTable> {
	@Transactional
	List<RestaurantTable> findAllByRestaurant(final Restaurant restaurant);
}
