package com.nullchefo.restaurantbookings.service;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.nullchefo.restaurantbookings.entity.Driver;
import com.nullchefo.restaurantbookings.repository.DriverRepository;

@Service
public class DriverService extends BaseService<Driver>{
	private final DriverRepository driverRepository;

	public DriverService(final DriverRepository driverRepository) {
		this.driverRepository = driverRepository;
	}

	@Override
	protected JpaRepository<Driver, UUID> getRepo() {
		return this.driverRepository;
	}
}
