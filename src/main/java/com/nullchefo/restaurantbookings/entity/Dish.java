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
@NoArgsConstructor//Need public constructor for serialization
@Builder
@AllArgsConstructor
public class Dish implements Serializable {

	private String id;
	private String name;
	private List<String> ingredients;

}
