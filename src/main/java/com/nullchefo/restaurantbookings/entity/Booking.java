package com.nullchefo.restaurantbookings.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
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
	@ManyToMany
	private List<User> users;
	private String customerInformation;
	private LocalDateTime date;
	@ManyToOne(optional = true)
	private Order preOrderMeal;
	private byte reservedSeats;
	private String notes;

}
