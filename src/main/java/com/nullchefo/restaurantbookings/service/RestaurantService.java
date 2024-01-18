package com.nullchefo.restaurantbookings.service;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.nullchefo.restaurantbookings.entity.Restaurant;
import com.nullchefo.restaurantbookings.repository.RestaurantRepository;

@Service
public class RestaurantService extends BaseService<Restaurant> {

	private final RestaurantRepository restaurantRepository;

	public RestaurantService(final RestaurantRepository restaurantRepository) {
		this.restaurantRepository = restaurantRepository;
	}

	@Override
	protected JpaRepository<Restaurant, UUID> getRepo() {
		return this.restaurantRepository;
	}
}
