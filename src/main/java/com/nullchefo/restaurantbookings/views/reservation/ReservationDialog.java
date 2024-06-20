package com.nullchefo.restaurantbookings.views.reservation;

import com.nullchefo.restaurantbookings.entity.Reservation;
import com.nullchefo.restaurantbookings.service.ReservationService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;


public class ReservationDialog extends Dialog {

	private final Reservation reservation;

	private final ReservationService reservationService;

	private Button saveButton;
	private Binder<Reservation> binder;

	public ReservationDialog(Reservation reservation, ReservationService reservationService) {
		this.reservation = reservation;
		this.reservationService = reservationService;
		initContent();
	}

	private void initContent() {
		setHeaderTitle("Edit");
		configureContent();
		configureFooter();
		setCloseOnOutsideClick(false);
		setCloseOnEsc(true);
	}

	private void configureContent() {

		binder = new Binder<>(Reservation.class);

		TextField note = new TextField("Note");






		binder.forField(note).bind(Reservation::getNotes, Reservation::setNotes);

		binder.readBean(reservation);
		FormLayout formLayout = new FormLayout(note);
		add(formLayout);
	}

	private void configureFooter() {
		saveButton = new Button("Save");
		saveButton.addClickListener(save());
		saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		Button cancelButton = new Button("Cancel", e -> close());
		getFooter().add(cancelButton);
		getFooter().add(saveButton);
	}

	private ComponentEventListener<ClickEvent<Button>> save() {
		return ll -> {
			try {
				binder.writeBean(reservation);
				reservationService.create(reservation);
				close();
			} catch (ValidationException e) {
				throw new RuntimeException(e);
			}
		};
	}

	public void addSaveClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
		saveButton.addClickListener(listener);
	}
}
