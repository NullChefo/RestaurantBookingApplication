package com.nullchefo.restaurantbookings.service;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.nullchefo.restaurantbookings.entity.Donation;
import com.nullchefo.restaurantbookings.repository.DonationRepository;

@Service
public class DonationService extends BaseService<Donation> {

	private final DonationRepository donationRepository;

	public DonationService(final DonationRepository donationRepository) {
		this.donationRepository = donationRepository;
	}

	@Override
	protected JpaRepository<Donation, UUID> getRepo() {
		return this.donationRepository;
	}
}
