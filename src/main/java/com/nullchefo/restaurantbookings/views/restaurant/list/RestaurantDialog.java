package com.nullchefo.restaurantbookings.views.restaurant.list;

import org.springframework.data.domain.PageRequest;

import com.nullchefo.restaurantbookings.entity.Location;
import com.nullchefo.restaurantbookings.entity.Restaurant;
import com.nullchefo.restaurantbookings.entity.User;
import com.nullchefo.restaurantbookings.service.LocationService;
import com.nullchefo.restaurantbookings.service.RestaurantService;
import com.nullchefo.restaurantbookings.views.location.LocationDialog;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;

public class RestaurantDialog extends Dialog {

	private final Restaurant restaurant;
	private final RestaurantService restaurantService;
	private final LocationService locationService;
	private final User user;
	private Button saveButton;
	private Button addLocationButton;
	private Button editLocationButton;
	private Button removeLocationButton;
	private Binder<Restaurant> binder;

	private ComboBox<Location> locationComboBox;

	public RestaurantDialog(
			Restaurant restaurant, RestaurantService restaurantService,
			LocationService locationService, User user) {
		this.restaurant = restaurant;
		this.restaurantService = restaurantService;
		this.locationService = locationService;
		this.user = user;
		initContent();
	}

	private void initContent() {
		setHeaderTitle(restaurant.getId() != null ? "Edit" : "Add");
		configureContent();
		configureFooter();
/*        setWidth("300px");
        setHeight("500px");*/
		setCloseOnOutsideClick(false);
		setCloseOnEsc(true);
	}

	private void configureContent() {
		TextField name = new TextField("Name");
		name.setRequired(true);
		TextField pictureURL = new TextField("Picture url");

		this.locationComboBox = new ComboBox<>("Location");
		this.locationComboBox.setItemLabelGenerator(Location::getAddress);
		loadLocationComboBox();
		this.locationComboBox.setRequired(true);

		this.locationComboBox.setItems(query -> locationService.listForUser(
				PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)),
				this.user));

		this.addLocationButton = new Button("Add Location");
		this.editLocationButton.addClickListener(e -> {
			LocationDialog dialog = new LocationDialog(this.locationComboBox.getValue(), locationService, user);
			dialog.addSaveClickListener(buttonClickEvent -> {
				loadLocationComboBox();
				dialog.close();
			});
			dialog.open();
		});

		this.editLocationButton = new Button("Edit Location");

		if (this.locationComboBox.getValue() == null) {
			editLocationButton.setEnabled(false);
		} else {
			editLocationButton.setEnabled(true);
			this.editLocationButton.addClickListener(e -> {
				LocationDialog dialog = new LocationDialog(this.locationComboBox.getValue(), locationService, user);
				dialog.addSaveClickListener(ll -> {
					loadLocationComboBox();
					dialog.close();
				});
				dialog.open();
			});
		}

		this.removeLocationButton = new Button("Remove Location");
		this.removeLocationButton.addClickListener(e -> {
			this.locationComboBox.setValue(null);
			locationService.delete(this.locationComboBox.getValue().getId());
			loadLocationComboBox();
		});

		binder = new Binder<>(Restaurant.class);
		binder.forField(name).asRequired("Name is required").bind(Restaurant::getName, Restaurant::setName)
			  .setAsRequiredEnabled(true);

		binder.forField(pictureURL).bind(Restaurant::getPictureURL, Restaurant::setPictureURL);

		binder
				.forField(this.locationComboBox)
				.asRequired("Location is required")
				.bind(Restaurant::getLocation, Restaurant::setLocation)
				.setAsRequiredEnabled(true);
		;

		binder.readBean(restaurant);

		HorizontalLayout locationLayout = new HorizontalLayout();
		locationLayout.add(this.locationComboBox, addLocationButton);

		FormLayout formLayout = new FormLayout(name, pictureURL, locationLayout);
		add(formLayout);
	}

	private void loadLocationComboBox() {
		this.locationComboBox.setItems(locationService.findAllByUser(this.user));
	}

	private void configureFooter() {
		saveButton = new Button("Create");
		saveButton.addClickListener(save());
		saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		Button cancelButton = new Button("Cancel", e -> close());
		getFooter().add(cancelButton);
		getFooter().add(saveButton);
	}

	private ComponentEventListener<ClickEvent<Button>> save() {
		return ll -> {
			try {
				binder.writeBean(restaurant);
				if (restaurant.getId() == null) {
					restaurant.setOwner(this.user);
				}
				// todo check if relevant to the scope
				//				if(restaurant.getOwner() != this.user || !user.getRoles().contains(RoleEnum.ADMIN)) {
				//					Notification.show("You can't edit restaurant that does not belong to you!", 3000, Notification.Position.MIDDLE);
				//					throw new RuntimeException(String.format("User with id %s is trying to edit restaurant with id: %s", user.getId(), restaurant.getId()));
				//				}
				restaurantService.create(restaurant);

				// TODO link location
				close();
				//reloadGrid();
			} catch (ValidationException e) {
				throw new RuntimeException(e);
			}
		};
	}

	public void addSaveClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
		saveButton.addClickListener(listener);
	}

}
