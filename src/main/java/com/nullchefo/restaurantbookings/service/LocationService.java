package com.nullchefo.restaurantbookings.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.nullchefo.restaurantbookings.entity.Location;
import com.nullchefo.restaurantbookings.repository.LocationRepository;

@Service
public class LocationService extends BaseService<Location> {

	private final LocationRepository locationRepository;

	public LocationService(LocationRepository locationRepository) {
		this.locationRepository = locationRepository;
	}

	@Override
	protected JpaRepository<Location, UUID> getRepo() {
		return this.locationRepository;
	}

	public Page<Location> list(Pageable pageable, Specification<Location> filter) {
		return locationRepository.findAll(filter, pageable);
	}

}
