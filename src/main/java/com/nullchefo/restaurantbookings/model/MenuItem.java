package com.nullchefo.restaurantbookings.model;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class MenuItem  extends BaseEntity{
	@ManyToOne
	@JoinColumn(name = "menu_id")
	private Menu menu;


	// availability time 09-12 every day


	private String name;

	private String description;


	// TODO make list with entity
	private String ingredients;

	// TODO think twice
	private float price;

}
