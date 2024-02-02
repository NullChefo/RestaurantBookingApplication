package com.nullchefo.restaurantbookings.views.location;

import com.nullchefo.restaurantbookings.entity.Location;
import com.nullchefo.restaurantbookings.entity.User;
import com.nullchefo.restaurantbookings.service.LocationService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

public class LocationDialog extends Dialog {

	private final LocationService locationService;
	private final User user;
	private Location location;
	private Button saveButton;
	private Binder<Location> binder;

	private Button removeLocationButton;

	public LocationDialog(Location location, LocationService locationService, User user) {
		this.location = location;
		this.locationService = locationService;
		this.user = user;
		initContent();
	}

	private void initContent() {
		setHeaderTitle(location != null ? "Edit" : "Add");
		configureContent();
		configureFooter();
/*        setWidth("300px");
        setHeight("500px");*/
		setCloseOnOutsideClick(false);
		setCloseOnEsc(true);
	}

	private void configureContent() {
		TextField address = new TextField("Address");
		address.setRequired(true);
		TextField latitude = new TextField("Latitude");
		TextField longitude = new TextField("Longitude");

		binder = new Binder<>(Location.class);
		binder.forField(address).asRequired("Address is required").bind(Location::getAddress, Location::setAddress)
			  .setAsRequiredEnabled(true);

		binder.forField(latitude).bind(Location::getLatitude, Location::setLatitude);
		binder.forField(longitude).bind(Location::getLongitude, Location::setLongitude);

		binder.readBean(location);
		FormLayout formLayout = new FormLayout(address, latitude, longitude);
		add(formLayout);
	}

	private void configureFooter() {
		saveButton = new Button("Save");
		saveButton.addClickListener(save());
		saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		Button cancelButton = new Button("Cancel", e -> close());

		getFooter().add(cancelButton);

		if (location != null) {
			this.removeLocationButton = new Button("Remove Location");
			removeLocationButton.addClickListener(e -> {
				try {
					locationService.delete(this.location.getId());
				} catch (Exception err) {
					Notification.show(
							"Cannot delete location because it is in use!",
							3000,
							Notification.Position.BOTTOM_START);
				}
			});
			getFooter().add(removeLocationButton);
		}

		getFooter().add(saveButton);

	}

	private ComponentEventListener<ClickEvent<Button>> save() {
		return ll -> {
			try {
				if (location == null) {
					location = new Location();
				}
				binder.writeBean(location);

				if (location.getId() == null) {
					location.setUser(this.user);
				}
				locationService.create(location);
				close();
			} catch (ValidationException e) {
				throw new RuntimeException(e);
			}
		};
	}

	public void addSaveClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
		saveButton.addClickListener(listener);
		if (this.removeLocationButton != null) {
			removeLocationButton.addClickListener(listener);
		}
	}

}
