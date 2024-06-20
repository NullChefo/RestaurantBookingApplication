package com.nullchefo.restaurantbookings.entity;

import java.util.List;

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
public class Ingredient {

	private List<String> vegetables;
	private List<String> spices;
	private List<String> others;
	private List<String> nuts;
	private List<String> meat;
	private List<String> grain;
	private List<String> dairy;



}
