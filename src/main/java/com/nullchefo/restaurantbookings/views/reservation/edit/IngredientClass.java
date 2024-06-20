package com.nullchefo.restaurantbookings.views.reservation.edit;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
public class IngredientClass {

	private String id;
	private String name;
	private String type;

	public static String convertIngridentClassSetToString(Set<IngredientClass> ingredientClasses) {
		return "IgnoreIngredients=" + ingredientClasses.stream()
													   .map(IngredientClass::getName)
													   .collect(Collectors.joining(","));
	}


}
