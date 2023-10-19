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
@Entity
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;


	// TODO fix USER entity exist

	@ManyToMany
	@JoinTable(name = "customer_favorite_restaurants",
			   joinColumns = @JoinColumn(name = "customer_id"),
			   inverseJoinColumns = @JoinColumn(name = "restaurant_id")
	)
	private List<Restaurant> favoriteRestaurants;

	// Other customer attributes (name, contact information, etc.)


	// TODO make it List<Location>
	private String locations;
}
