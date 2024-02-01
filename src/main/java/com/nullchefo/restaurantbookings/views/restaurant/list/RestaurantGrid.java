package com.nullchefo.restaurantbookings.views.restaurant.list;

import com.nullchefo.restaurantbookings.entity.Restaurant;
import com.vaadin.flow.component.grid.Grid;

public class RestaurantGrid extends Grid<Restaurant> {
	public RestaurantGrid() {
		super(Restaurant.class, false);
		configureView();
	}

	private void configureView() {
		addColumn("name").setHeader("Name").setFrozen(true);
		addColumn(restaurant -> restaurant.getOwner().getFirstName() + " " + restaurant.getOwner().getLastName())
				.setHeader("owner")
				.setSortable(true);
		addColumn(restaurant -> restaurant.getLocation().getAddress()).setHeader("location").setSortable(true);
		addColumn(Restaurant::getPictureURL).setHeader("picture");
		setSizeFull();
	}
}
