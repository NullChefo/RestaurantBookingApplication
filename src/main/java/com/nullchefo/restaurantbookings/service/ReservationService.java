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

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.nullchefo.restaurantbookings.entity.Reservation;
import com.nullchefo.restaurantbookings.entity.RestaurantTable;
import com.nullchefo.restaurantbookings.repository.ReservationRepository;

@Service
public class ReservationService extends BaseService<Reservation> {
	private final ReservationRepository reservationRepository;

	@Autowired
	public ReservationService(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}

	@Override
	protected JpaRepository<Reservation, UUID> getRepo() {
		return this.reservationRepository;
	}

	public List<Reservation> finAllByReservationDate(final LocalDate date) {
		return this.reservationRepository.findAllByReservationTime(date);
	}

	public List<Reservation> findAllByReservationTimeAndTableIn(
			final LocalDate date,
			final List<RestaurantTable> tables) {
		return this.reservationRepository.findAllByReservationTimeAndTableIn(date, tables);
	}

}
