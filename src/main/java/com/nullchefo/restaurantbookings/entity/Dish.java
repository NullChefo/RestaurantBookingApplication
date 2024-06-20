package com.nullchefo.restaurantbookings.entity;

import java.io.Serializable;
import java.util.List;

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
public class Dish implements Serializable {

	private String id;
	private String name;
	private Ingredient ingredient; // Assuming Ingredient class is also Serializable

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Dish id=" + id + ", name=" + name + " With: \n");
		sb.append("Ingredients=[" + ingredient.toString() + "]"); // Make sure Ingredient has a proper toString() method
		return sb.toString();
	}

}
