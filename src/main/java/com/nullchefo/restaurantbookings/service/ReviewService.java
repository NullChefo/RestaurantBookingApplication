package com.nullchefo.restaurantbookings.service;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.nullchefo.restaurantbookings.entity.Review;
import com.nullchefo.restaurantbookings.repository.ReviewRepository;

@Service
public class ReviewService extends BaseService<Review> {
	private final ReviewRepository bookingRepository;

	public ReviewService(final ReviewRepository bookingRepository) {
		this.bookingRepository = bookingRepository;
	}

	@Override
	protected JpaRepository<Review, UUID> getRepo() {
		return this.bookingRepository;
	}
}
