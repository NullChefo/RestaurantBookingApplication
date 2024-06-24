package com.nullchefo.restaurantbookings.views.reservation;

import java.util.Optional;

import com.nullchefo.restaurantbookings.entity.Reservation;
import com.nullchefo.restaurantbookings.entity.RestaurantTable;
import com.vaadin.flow.component.grid.Grid;

public class ReservationGrid extends Grid<Reservation> {
	public ReservationGrid() {
		super(Reservation.class, false);
		configureView();
	}

	private void configureView() {

		addColumn(reservation -> Optional.ofNullable(reservation.getUser())
										 .map(owner -> owner.getFirstName() + " " + owner.getLastName())
										 .orElse(""))
				.setHeader("Customer")
				.setSortable(true);
		addColumn(Reservation::getReservationTime).setHeader("Time");

		addColumn(reservation -> Optional.ofNullable(reservation.getTable())
										 .map(RestaurantTable::getName)
										 .orElse(""))
				.setHeader("Table name")
				.setSortable(true);

		addColumn(reservation -> Optional.ofNullable(reservation.getTable())
										 .map(RestaurantTable::getCapacity)
										 .orElse(null))
				.setHeader("Table capacity")
				.setSortable(true);

		addColumn(reservation -> reservation.getTable().getName()).setHeader("Table");
		addColumn(Reservation::getNotes).setHeader("Notes");
		setSizeFull();
	}
}
