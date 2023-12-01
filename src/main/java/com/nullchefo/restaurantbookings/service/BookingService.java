package com.nullchefo.restaurantbookings.service;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.nullchefo.restaurantbookings.entity.Booking;
import com.nullchefo.restaurantbookings.repository.BookingRepository;

@Service
public class BookingService extends BaseService<Booking>{

	private final BookingRepository bookingRepository;

	public BookingService(final BookingRepository bookingRepository) {
		this.bookingRepository = bookingRepository;
	}

	@Override
	protected JpaRepository<Booking, UUID> getRepo() {
		return this.bookingRepository;
	}
}
