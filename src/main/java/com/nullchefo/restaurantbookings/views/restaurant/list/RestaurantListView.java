/*
 * Copyright 2024 Stefan Kehayov
 *
 * All rights reserved. Unauthorized use, reproduction, or distribution
 * of this software, or any portion of it, is strictly prohibited.
 *
 * The software is provided "as is", without warranty of any kind,
 * express or implied, including but not limited to the warranties
 * of merchantability, fitness for a particular purpose, and noninfringement.
 * In no event shall the authors or copyright holders be liable for any claim,
 * damages, or other liability, whether in an action of contract, tort, or otherwise,
 * arising from, out of, or in connection with the software or the use or other dealings
 * in the software.
 *
 * Usage of this software by corporations, for machine learning, or AI purposes
 * is expressly prohibited.
 */
package com.nullchefo.restaurantbookings.views.restaurant.list;

import java.util.List;

import com.nullchefo.restaurantbookings.entity.Restaurant;
import com.nullchefo.restaurantbookings.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@PageTitle("Restaurants")
@Route(value = "restaurant", layout = MainLayout.class)
@PermitAll
public class RestaurantListView extends VerticalLayout {

	private final Grid<Restaurant> grid = new Grid<>(Restaurant.class);

	public RestaurantListView(List<Restaurant> restaurants) {
		// TODO change
		grid.setItems(restaurants);
		grid.setColumns(
				"name",
				"owner",
				"location",
				"menus",
				"schedules",
				"donations",
				"cuisines",
				"reviews",
				"tables");

		add(grid);
	}
}
