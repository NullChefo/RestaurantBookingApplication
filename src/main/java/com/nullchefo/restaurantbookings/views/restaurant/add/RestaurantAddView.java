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
package com.nullchefo.restaurantbookings.views.restaurant.add;

import static com.nullchefo.restaurantbookings.entity.enums.RoleEnum.ADMIN;

import com.nullchefo.restaurantbookings.entity.Restaurant;
import com.nullchefo.restaurantbookings.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import com.nullchefo.restaurantbookings.entity.enums.RoleEnum;

@PageTitle("Restaurants")
@Route(value = "restaurant-add", layout = MainLayout.class)
@RolesAllowed({"ADMIN", "ORGANISATION"})
@PermitAll
public class RestaurantAddView extends FormLayout {

	private TextField name = new TextField("Name");
	private TextField owner = new TextField("Owner");
	private TextField location = new TextField("Location");
	private TextField menus = new TextField("Menus");
	private TextField schedules = new TextField("Schedules");
	private TextField bookings = new TextField("Bookings");
	private TextField donations = new TextField("Donations");
	private TextField cuisines = new TextField("Cuisines");
	private TextField reviews = new TextField("Reviews");
	private TextField tables = new TextField("Tables");

	private Button save = new Button("Save");

	private Binder<Restaurant> binder = new Binder<>(Restaurant.class);

	public RestaurantAddView() {
		add(name, owner, location, menus, schedules, bookings, donations, cuisines, reviews, tables, save);

		binder.bindInstanceFields(this);

		save.addClickListener(event -> save());
	}

	private void save() {
		// Implement the save logic here
	}
}
