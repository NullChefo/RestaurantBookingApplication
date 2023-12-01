package com.nullchefo.restaurantbookings.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Donation extends BaseEntity {

	private LocalDateTime donationTime;
	private String donatedFoodDescription;
	private int quantity;

	// Other donation-related properties and methods

	// You may include recipient information if necessary

	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;

	@ManyToOne
	private DonationRecipient donationRecipient; // Added relationship to HomelessPerson

}

