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
package com.nullchefo.restaurantbookings.views.reservation.list;

import java.util.ArrayList;
import java.util.List;

import com.nullchefo.restaurantbookings.configuration.security.AuthenticatedUser;
import com.nullchefo.restaurantbookings.entity.Reservation;
import com.nullchefo.restaurantbookings.entity.Restaurant;
import com.nullchefo.restaurantbookings.entity.RestaurantTable;
import com.nullchefo.restaurantbookings.entity.User;
import com.nullchefo.restaurantbookings.entity.enums.RoleEnum;
import com.nullchefo.restaurantbookings.service.ReservationService;
import com.nullchefo.restaurantbookings.service.RestaurantService;
import com.nullchefo.restaurantbookings.service.RestaurantTableService;
import com.nullchefo.restaurantbookings.views.MainLayout;
import com.nullchefo.restaurantbookings.views.reservation.ReservationGrid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@PageTitle("Reservation")
@Route(value = "reservation", layout = MainLayout.class)
@PermitAll
public class ListReservationsView extends VerticalLayout {

	private final ReservationService reservationService;
	private final AuthenticatedUser authenticatedUser;

	private final RestaurantService restaurantService;

	private final RestaurantTableService restaurantTableService;

	private User user;

	private ReservationGrid reservationGrid;



	public ListReservationsView(ReservationService reservationService, AuthenticatedUser authenticatedUser,
								RestaurantService restaurantService, RestaurantTableService restaurantTableService) {
		this.reservationService = reservationService;
		this.authenticatedUser = authenticatedUser;
		this.restaurantService = restaurantService;
		this.restaurantTableService = restaurantTableService;

		this.user = this.authenticatedUser.get().orElse(null);

		setSizeFull();
		add(createReservationsGrid());

	}

	private ReservationGrid createReservationsGrid() {
		this.reservationGrid = new ReservationGrid();

		List<Reservation> reservations = new ArrayList<>();

		if (this.user != null) {
			if(user.getRoles().contains(RoleEnum.ADMIN)) {
				reservations = reservationService.findAllSortedByReservationTime();
			}

			if (user.getRoles().contains(RoleEnum.ORGANISATION)) {
				List<Restaurant> restaurants = this.restaurantService.findAllByOwner(user);
				List<RestaurantTable> tables = this.restaurantTableService.findAllByRestaurants(restaurants);
				reservations = this.reservationService.findAllByRestaurantTablesAndSortedByReservationTime(tables);
			}

			if (user.getRoles().contains(RoleEnum.CUSTOMER)) {
				reservations = this.reservationService.findAllForUserAndSortedByTime(this.user);
			}

		}

		this.reservationGrid.setItems(reservations);

		return this.reservationGrid;
	}

}
