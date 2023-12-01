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

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class Booking extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;

	// Make it to be more than one customer per booking
	@ManyToOne
	private User user;

	private String customerInformation;

	private LocalDateTime date;

	// nullable
	@ManyToOne
	private Order preOrderMeal;

	private byte reservedSeats;

	private String notes;



}
