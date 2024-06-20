package com.nullchefo.restaurantbookings.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ingredient implements Serializable {

	private List<String> vegetables;
	private List<String> spices;
	private List<String> others;
	private List<String> nuts;
	private List<String> meat;
	private List<String> grain;
	private List<String> dairy;

	public List<String> getAllIngredients() {
		List<List<String>> ingredientLists = Arrays.asList(
				vegetables,
				spices,
				others,
				nuts,
				meat,
				grain,
				dairy
														  );

		return ingredientLists.stream()
							  .filter(Objects::nonNull)
							  .flatMap(List::stream)
							  .collect(Collectors.toList());
	}

	@Override
	public String toString() {
		StringJoiner sj = new StringJoiner("; ");

		if (vegetables != null && !vegetables.isEmpty()) {
			sj.add("Vegetables=" + String.join(",", vegetables));
		}

		if (spices != null && !spices.isEmpty()) {
			sj.add("Spices=" + String.join(",", spices));
		}

		if (others != null && !others.isEmpty()) {
			sj.add("Others=" + String.join(",", others));
		}

		if (nuts != null && !nuts.isEmpty()) {
			sj.add("Nuts=" + String.join(",", nuts));
		}

		if (meat != null && !meat.isEmpty()) {
			sj.add("Meat=" + String.join(",", meat));
		}

		if (grain != null && !grain.isEmpty()) {
			sj.add("Grain=" + String.join(",", grain));
		}

		if (dairy != null && !dairy.isEmpty()) {
			sj.add("Dairy=" + String.join(",", dairy));
		}

		return sj.toString();
	}
}
