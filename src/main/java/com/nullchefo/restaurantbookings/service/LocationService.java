/*
 * Copyright 2024 Stefan Kehayov
 *
 * All rights reserved. Unauthorized use, reproduction, or distribution
 * of this software, or any portion of it, is strictly prohibited.
 *
 * The software is provided "as is", without warranty of any kind,
 * express or implied, including but not limited to the warranties
 * of merchantability, fitness for a particular purpose, and noninfringement.
 * In no event shall the authors or copyright holders be liable for any claim,
 * damages, or other liability, whether in an action of contract, tort, or otherwise,
 * arising from, out of, or in connection with the software or the use or other dealings
 * in the software.
 *
 * Usage of this software by corporations, for machine learning, or AI purposes
 * is expressly prohibited.
 */
package com.nullchefo.restaurantbookings.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.nullchefo.restaurantbookings.entity.Location;
import com.nullchefo.restaurantbookings.entity.Restaurant;
import com.nullchefo.restaurantbookings.entity.User;
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

	public Stream<Location> list(PageRequest of) {
		return locationRepository.findAll(of).stream();
	}

	public Stream<Location> listForUser(PageRequest of, User user) {
		return locationRepository.findAllByUser(user, of).stream();
	}

	public List<Location> findAllByUser(final User user) {
		return locationRepository.findAllByUser(user);
	}

	public List<Location> findAllByRestaurant(final Restaurant restaurant) {
		return locationRepository.findAllByRestaurant(restaurant);
	}
}

