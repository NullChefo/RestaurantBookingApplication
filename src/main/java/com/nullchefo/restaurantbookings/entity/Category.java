package com.nullchefo.restaurantbookings.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Category extends BaseEntity {

	@ManyToOne
	private Menu menu;

	@OneToMany(mappedBy = "category")
	private List<MenuItem> items;

	// ... other fields and annotations
}
