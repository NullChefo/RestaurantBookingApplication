package com.nullchefo.restaurantbookings.model;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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
