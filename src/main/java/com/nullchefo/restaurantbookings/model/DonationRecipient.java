package com.nullchefo.restaurantbookings.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
// TODO can be user with role DonationRecipient
@Entity
public class DonationRecipient {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String name;
	@ManyToOne
	private Donation donation;

}
