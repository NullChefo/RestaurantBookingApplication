package com.nullchefo.restaurantbookings.service;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.nullchefo.restaurantbookings.entity.Cuisine;
import com.nullchefo.restaurantbookings.repository.CuisineRepository;
@Service
public class CuisineService extends BaseService<Cuisine>{

	private final CuisineRepository cuisineRepository;

	public CuisineService(final CuisineRepository cuisineRepository) {
		this.cuisineRepository = cuisineRepository;
	}

	@Override
	protected JpaRepository<Cuisine, UUID> getRepo() {
		return this.cuisineRepository;
	}
}
