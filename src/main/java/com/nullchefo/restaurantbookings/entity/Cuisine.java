package com.nullchefo.restaurantbookings.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Вид кухня
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class Cuisine extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;

	private String name;

	private String description;

	// Other cuisine attributes (name, description, etc.)
}
