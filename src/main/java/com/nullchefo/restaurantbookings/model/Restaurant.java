package com.nullchefo.restaurantbookings.model;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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
