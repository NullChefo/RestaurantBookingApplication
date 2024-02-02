package com.nullchefo.restaurantbookings.views.reservation;


import java.time.LocalDate;
import java.util.List;

import com.nullchefo.restaurantbookings.entity.Reservation;
import com.nullchefo.restaurantbookings.entity.Restaurant;
import com.nullchefo.restaurantbookings.entity.RestaurantTable;
import com.nullchefo.restaurantbookings.entity.User;
import com.nullchefo.restaurantbookings.service.ReservationService;
import com.nullchefo.restaurantbookings.service.RestaurantService;
import com.nullchefo.restaurantbookings.service.RestaurantTableService;
import com.nullchefo.restaurantbookings.views.restaurantTable.RestaurantTableGrid;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
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

	private final ReservationService  reservationService;


	private Button reserveTableButton;

	private RestaurantTableGrid availableTablesGrid;
	private ReservationGrid reservationGrid;

	private DatePicker datePicker;



	public ReserveTableDialog(
			User user,
			Restaurant restaurant,
			RestaurantTableService restaurantTableService,
			RestaurantService restaurantService, ReservationService reservationService) {
		this.user = user;
		this.restaurant = restaurant;
		this.restaurantTableService = restaurantTableService;
		this.restaurantService = restaurantService;
		this.reservationService = reservationService;
		initContent();
	}

	private void initContent() {

		configureContent();

		setCloseOnOutsideClick(false);
		setCloseOnEsc(true);

		setMaxHeight("90vh");
		setMaxWidth("90vw");


	}

	private void configureContent() {
		HorizontalLayout  buttonsLayout = new HorizontalLayout();
		buttonsLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
		buttonsLayout.add(createDatePicker());
		buttonsLayout.add(createReserveTableButton());

		add(buttonsLayout, createRestaurantTableGrid());

		add(createReservationGrid());

		refreshGridElements();
	}

// add filter reserve teble for DATE
	private Grid<RestaurantTable> createRestaurantTableGrid() {
		this.availableTablesGrid = new RestaurantTableGrid();
		this.availableTablesGrid.setMinHeight("400px");
		this.availableTablesGrid.setMinWidth("500px");
//		this.restaurantGrid.addItemDoubleClickListener(l -> openRestaurantDialog(l.getItem(), this.authenticatedUser.get()
//																											   .orElse(null)));
		SingleSelect<Grid<RestaurantTable>, RestaurantTable> singleSelect = availableTablesGrid.asSingleSelect();
		singleSelect.addValueChangeListener(l -> {
			RestaurantTable value = l.getValue();
			boolean enabled = value != null;
				reserveTableButton.setEnabled(enabled);
		});
		return availableTablesGrid;
	}

	private Grid<Reservation> createReservationGrid() {
		this.reservationGrid = new ReservationGrid();
			this.reservationGrid.setMinHeight("400p");
			this.reservationGrid.setMinWidth("500px");


		SingleSelect<Grid<Reservation>, Reservation> singleSelect = reservationGrid.asSingleSelect();
		singleSelect.addValueChangeListener(l -> {
			Reservation value = l.getValue();
			boolean enabled = value != null;
			reserveTableButton.setEnabled(enabled);
		});

		return reservationGrid;
	}



	private HorizontalLayout createDatePicker() {
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		datePicker = new DatePicker("Reservation date");
		datePicker.setRequired(true);
		Button searchButton = new Button(new Icon("lumo", "search"));
		searchButton.addClickListener(l -> { refreshGridElements();
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
			RestaurantTable value = availableTablesGrid.asSingleSelect().getValue();
			dialog.setHeaderTitle("Are you sure you want to reserve this table for:" + datePicker.getValue());
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

	private void refreshGridElements() {

		if (datePicker.getValue() == null) {
			datePicker.setValue(LocalDate.now());
		}
		List<Reservation>  reservations = reservationService.finAllByReservationDate(datePicker.getValue());

		List<RestaurantTable> availableTables = this.restaurantTableService.findAllByRestaurant(this.restaurant);

		List<RestaurantTable> reservedTables = reservations.stream().map(Reservation::getTable).toList();

		availableTables.removeAll(reservedTables);

		this.reservationGrid.setItems(reservations);

		availableTablesGrid.deselectAll();

		this.availableTablesGrid.setItems(availableTables);

	}

	// TODO check
	public void addSaveClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
		reserveTableButton.addClickListener(listener);
	}



}
