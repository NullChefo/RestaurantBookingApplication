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
import com.nullchefo.restaurantbookings.views.reservation.ReservationDialog;
import com.nullchefo.restaurantbookings.views.reservation.ReservationGrid;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.selection.SingleSelect;
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
	private final User user;
	private Button cancelReservationButton;
	private Button editReservationButton;
	private ReservationGrid reservationGrid;

	public ListReservationsView(
			ReservationService reservationService, AuthenticatedUser authenticatedUser,
			RestaurantService restaurantService, RestaurantTableService restaurantTableService) {
		this.reservationService = reservationService;
		this.authenticatedUser = authenticatedUser;
		this.restaurantService = restaurantService;
		this.restaurantTableService = restaurantTableService;

		this.user = this.authenticatedUser.get().orElse(null);

		setSizeFull();
		HorizontalLayout buttonsLayout = new HorizontalLayout();
		buttonsLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
		buttonsLayout.add(createEditReservationButton(), createCancelReservationButton());

		add(buttonsLayout);
		add(createReservationsGrid());
		reloadGridElements();
	}

	private ReservationGrid createReservationsGrid() {
		this.reservationGrid = new ReservationGrid();
		SingleSelect<Grid<Reservation>, Reservation> singleSelect = reservationGrid.asSingleSelect();
		singleSelect.addValueChangeListener(l -> {
			Reservation value = l.getValue();
			boolean enabled = value != null;
			editReservationButton.setEnabled(enabled);
			cancelReservationButton.setEnabled(enabled);
		});

		return this.reservationGrid;
	}

	private void reloadGridElements() {
		List<Reservation> reservations = new ArrayList<>();

		if (this.user != null) {
			if (user.getRoles().contains(RoleEnum.ADMIN)) {
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
		this.reservationGrid.deselectAll();
	}

	private void openReservationDialog(final Reservation reservation) {
		ReservationDialog dialog = new ReservationDialog(reservation, this.reservationService);

		dialog.addSaveClickListener(ll -> {
			reloadGridElements();
			dialog.close();
		});
		dialog.open();
	}

	private Button createCancelReservationButton() {
		this.cancelReservationButton = new Button("Cancel Reservation");
		this.cancelReservationButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		this.cancelReservationButton.setTooltipText("Cancel Reservation");
		this.cancelReservationButton.setEnabled(false);
		this.cancelReservationButton.addClickListener(l -> {
			Dialog dialog = new Dialog();
			Reservation value = reservationGrid.asSingleSelect().getValue();
			dialog.setHeaderTitle("Are you sure you want to cancel the reservation for: " + value.getReservationTime());
			Button ok = new Button("Yes", ll -> {
				reservationService.delete(value.getId());
				reloadGridElements();
				dialog.close();
			});
			Button no = new Button("No", ll -> dialog.close());
			dialog.getFooter().add(ok, no);
			dialog.open();
		});

		return this.cancelReservationButton;
	}

	private Button createEditReservationButton() {
		this.editReservationButton = new Button("Edit Reservation");
		this.editReservationButton.setEnabled(false);
		editReservationButton.addClickListener(l -> openReservationDialog(reservationGrid.asSingleSelect().getValue()));
		return editReservationButton;
	}

}
