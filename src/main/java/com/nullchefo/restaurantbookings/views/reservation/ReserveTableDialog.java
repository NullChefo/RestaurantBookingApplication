package com.nullchefo.restaurantbookings.views.reservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.nullchefo.restaurantbookings.entity.Reservation;
import com.nullchefo.restaurantbookings.entity.Restaurant;
import com.nullchefo.restaurantbookings.entity.RestaurantTable;
import com.nullchefo.restaurantbookings.entity.User;
import com.nullchefo.restaurantbookings.entity.enums.RoleEnum;
import com.nullchefo.restaurantbookings.service.OntologyManagerService;
import com.nullchefo.restaurantbookings.service.ReservationService;
import com.nullchefo.restaurantbookings.service.RestaurantService;
import com.nullchefo.restaurantbookings.service.RestaurantTableService;
import com.nullchefo.restaurantbookings.views.reservation.edit.ReservationDialog;
import com.nullchefo.restaurantbookings.views.restaurantTable.RestaurantTableGrid;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.selection.SingleSelect;

public class ReserveTableDialog extends Dialog {

	private final User user;
	private final Restaurant restaurant;

	private final RestaurantTableService restaurantTableService;

	private final RestaurantService restaurantService;

	private final ReservationService reservationService;

	private Button reserveTableButton;

	private Button cancelReservationButton;

	private Button editReservationButton;

	private RestaurantTableGrid availableTablesGrid;
	private ReservationGrid reservationGrid;

	private DateTimePicker datePicker;


	private final OntologyManagerService ontologyManagerService;

	public ReserveTableDialog(
			User user,
			Restaurant restaurant,
			RestaurantTableService restaurantTableService,
			RestaurantService restaurantService, ReservationService reservationService,
			final OntologyManagerService ontologyManagerService) {
		this.user = user;
		this.restaurant = restaurant;
		this.restaurantTableService = restaurantTableService;
		this.restaurantService = restaurantService;
		this.reservationService = reservationService;
		this.ontologyManagerService = ontologyManagerService;
		initContent();
	}

	private void initContent() {

		configureContent();

		setCloseOnOutsideClick(true);
		setCloseOnEsc(true);

		setMinHeight("90vh");
		setMinWidth("90vw");

	}

	private void configureContent() {
		HorizontalLayout buttonsLayout = new HorizontalLayout();
		buttonsLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
		buttonsLayout.add(createDatePicker());
		buttonsLayout.add(createReserveTableButton());

		add(buttonsLayout, createAvailableRestaurantTableGrid());

		HorizontalLayout reservedRestaurantTableActionButtonsHorizontalLayout = new HorizontalLayout();
		reservedRestaurantTableActionButtonsHorizontalLayout.setAlignItems(FlexComponent.Alignment.BASELINE);

		if (userIsTheUserOfReservationOrUserIsWithElevatedRights()) {
			reservedRestaurantTableActionButtonsHorizontalLayout.add(createCancelReservationButton());
			reservedRestaurantTableActionButtonsHorizontalLayout.add(createEditReservationButton());
		}

		add(reservedRestaurantTableActionButtonsHorizontalLayout);
		add(createReservedRestaurantTableGrid());

		reloadGridElements();
	}

	private Button createEditReservationButton() {
		this.editReservationButton = new Button("Edit Reservation");
		this.editReservationButton.setEnabled(false);
		editReservationButton.addClickListener(l -> openReservationDialog(reservationGrid.asSingleSelect().getValue()));
		return editReservationButton;
	}

	private void openReservationDialog(final Reservation reservation) {
		ReservationDialog dialog = new ReservationDialog(reservation, this.reservationService, ontologyManagerService);

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

	private boolean userIsTheUserOfReservationOrUserIsWithElevatedRights() {
		if (this.reservationGrid == null) {
			return true;
		}

		if (this.reservationGrid.asSingleSelect().getValue() != null) {
			// the reservation is made by the user
			Reservation reservation = this.reservationGrid.asSingleSelect().getValue();
			if (reservation.getUser().getId().equals(this.user.getId())) {
				return true;
			}
			// restaurant controls the reservation
			if (this.restaurant.getOwner() == this.user) {
				return true;
			}

			// if the user is the admin
			return this.user.getRoles().contains(RoleEnum.ADMIN);

		}
		return false;
	}

	// add filter reserve teble for DATE
	private Grid<RestaurantTable> createAvailableRestaurantTableGrid() {
		this.availableTablesGrid = new RestaurantTableGrid();
		this.availableTablesGrid.setMinHeight("400px");
		this.availableTablesGrid.setMinWidth("500px");

		this.availableTablesGrid.setMaxHeight("40vh");

		//		this.restaurantGrid.addItemDoubleClickListener(l -> openRestaurantDialog(l.getItem(), this.authenticatedUser.get()
		//																											   .orElse(null)));
		SingleSelect<Grid<RestaurantTable>, RestaurantTable> singleSelect = availableTablesGrid.asSingleSelect();
		singleSelect.addValueChangeListener(l -> {
			RestaurantTable value = l.getValue();
			boolean enabled = value != null;
			reserveTableButton.setEnabled(enabled);
			//			reservationGrid.setItems(reservationService.findAllByTableAndReservationTime(value, datePicker.getValue()));
		});
		return availableTablesGrid;
	}

	private Grid<Reservation> createReservedRestaurantTableGrid() {
		this.reservationGrid = new ReservationGrid();
		this.reservationGrid.setMinHeight("400p");
		this.reservationGrid.setMinWidth("500px");
		this.reservationGrid.setMaxHeight("40vh");
		SingleSelect<Grid<Reservation>, Reservation> singleSelect = reservationGrid.asSingleSelect();
		singleSelect.addValueChangeListener(l -> {
			Reservation value = l.getValue();
			boolean enabled = value != null;
			editReservationButton.setEnabled(enabled);
			cancelReservationButton.setEnabled(enabled);
		});

		return reservationGrid;
	}

	private HorizontalLayout createDatePicker() {
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
		datePicker = new DateTimePicker("Reservation date");
		//		datePicker.setRequired(true);
		Button searchButton = new Button(new Icon("lumo", "search"));
		searchButton.addClickListener(l -> {
			reloadGridElements();
		});
		horizontalLayout.add(datePicker, searchButton);
		return horizontalLayout;
	}

	private Button createReserveTableButton() {
		this.reserveTableButton = new Button("Reserve table");
		this.reserveTableButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		this.reserveTableButton.setTooltipText("Reserve table");
		this.reserveTableButton.setEnabled(false);
		this.reserveTableButton.addClickListener(l -> {
			Dialog dialog = new Dialog();
			LocalDateTime reservationTime = datePicker.getValue();
			reservationTime = reservationTime.withMinute(0).withSecond(0).withNano(0);

			RestaurantTable value = availableTablesGrid.asSingleSelect().getValue();
			dialog.setHeaderTitle("Are you sure you want to reserve this table for:" + reservationTime);
			Button ok = new Button("Yes", ll -> {
				Reservation reservation = Reservation.builder().table(value)
													 .reservationTime(datePicker.getValue())
													 .user(this.user)
													 .build();
				reservationService.create(reservation);
				dialog.close();
			});
			Button no = new Button("No", ll -> dialog.close());
			dialog.getFooter().add(ok, no);
			dialog.open();
		});

		return this.reserveTableButton;
	}

	private void reloadGridElements() {

		if (datePicker.getValue() == null) {
			LocalDateTime localDateTimeNow = LocalDateTime.now();
			localDateTimeNow = localDateTimeNow.withMinute(0).withSecond(0).withNano(0);
			datePicker.setValue(localDateTimeNow);
		}

		List<RestaurantTable> restaurantTables = this.restaurantTableService.findAllByRestaurant(this.restaurant);

		List<Reservation> reservations = reservationService.findAllByReservationTimeAndTableIn(
				datePicker.getValue(),
				restaurantTables);

		List<RestaurantTable> reservedTables = reservations.stream()
														   .map(Reservation::getTable)
														   .collect(Collectors.toList());

		// problem with equals and hashCode
		List<RestaurantTable> availableTables = restaurantTables.stream()
																.filter(table -> reservedTables
																		.stream()
																		.noneMatch(reservedTable -> reservedTable
																				.getId()
																				.equals(table.getId())))
																.collect(Collectors.toList());

		this.reservationGrid.setItems(reservations);
		availableTablesGrid.deselectAll();
		this.availableTablesGrid.setItems(availableTables);

	}

	// TODO check
	public void addSaveClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
		reserveTableButton.addClickListener(listener);
	}

}
