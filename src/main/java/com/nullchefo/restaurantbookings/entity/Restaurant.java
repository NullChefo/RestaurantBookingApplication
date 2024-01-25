package com.nullchefo.restaurantbookings.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
public class Restaurant extends BaseEntity {
	private String name;

	@ManyToOne
	private User owner;

	@OneToOne
	private Location location;

	@OneToMany(mappedBy = "restaurant")
	private List<Menu> menus;

	@OneToMany(mappedBy = "restaurant")
	private List<Schedule> schedules;

	@OneToMany(mappedBy = "restaurant")
	private List<Booking> bookings;

	// TODO fix
	@OneToMany(mappedBy = "restaurant")
	private List<Donation> donations; // Added relationship to Donation

	@OneToMany(mappedBy = "restaurant")
	private List<Cuisine> cuisines; // Added relationship to Donation

}
