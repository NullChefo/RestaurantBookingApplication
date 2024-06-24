package com.nullchefo.restaurantbookings.views.restaurantTable;

import com.nullchefo.restaurantbookings.entity.RestaurantTable;
import com.vaadin.flow.component.grid.Grid;

public class RestaurantTableGrid extends Grid<RestaurantTable> {
	public RestaurantTableGrid() {
		super(RestaurantTable.class, false);
		configureView();
	}

	private void configureView() {
		addColumn(RestaurantTable::getName).setHeader("name").setFrozen(true).setSortable(true);
		addColumn(RestaurantTable::getCapacity).setHeader("capacity").setSortable(true);
		setSizeFull();
	}
}
