package com.nullchefo.restaurantbookings.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;

	// Make it to be more than one customer per booking
	@ManyToOne
	private Customer customer;

	private String customerInformation;

	private LocalDateTime date;

	// nullable
	@ManyToOne
	private Order preOrderMeal;

	private byte reservedSeats;

	private String notes;



}
