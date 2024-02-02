package com.nullchefo.restaurantbookings.views.restaurant;

import java.util.Optional;

import com.nullchefo.restaurantbookings.entity.Location;
import com.nullchefo.restaurantbookings.entity.Restaurant;
import com.vaadin.flow.component.grid.Grid;

public class RestaurantGrid extends Grid<Restaurant> {
	public RestaurantGrid() {
		super(Restaurant.class, false);
		configureView();
	}

	private void configureView() {
		addColumn("name").setHeader("Name").setFrozen(true);

		addColumn(restaurant -> Optional.ofNullable(restaurant.getOwner())
										.map(owner -> owner.getFirstName() + " " + owner.getLastName())
										.orElse(""))
				.setHeader("owner")
				.setSortable(true);

		addColumn(restaurant -> Optional.ofNullable(restaurant.getLocation())
										.map(Location::getAddress)
										.orElse(""))
				.setHeader("location")
				.setSortable(true);

		addColumn(Restaurant::getPictureURL).setHeader("picture");
		setSizeFull();
	}
}
