package com.nullchefo.restaurantbookings.views.restaurantTable;

import com.nullchefo.restaurantbookings.entity.Restaurant;
import com.nullchefo.restaurantbookings.entity.RestaurantTable;
import com.nullchefo.restaurantbookings.service.RestaurantTableService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

public class RestaurantTableDialog extends Dialog {

	private final RestaurantTable restaurantTable;
	private final RestaurantTableService restaurantTableService;
	private final Restaurant restaurant;
	private Button saveButton;
	private Binder<RestaurantTable> binder;

	public RestaurantTableDialog(
			RestaurantTable restaurantTable,
			RestaurantTableService restaurantTableService,
			Restaurant restaurant) {
		this.restaurantTable = restaurantTable;
		this.restaurantTableService = restaurantTableService;
		this.restaurant = restaurant;
		initContent();
	}

	private void initContent() {
		setHeaderTitle(restaurantTable != null ? "Edit" : "Add");
		configureContent();
		configureFooter();
	/*        setWidth("300px");
			setHeight("500px");*/
		setCloseOnOutsideClick(false);
		setCloseOnEsc(true);
	}

	private void configureContent() {

		TextField name = new TextField("name");
		name.setRequired(true);
		IntegerField capacity = new IntegerField("capacity");
		capacity.setRequired(true);

		NumberField xPosition = new NumberField("x position");
		xPosition.setRequired(false);

		NumberField yPosition = new NumberField("y position");
		yPosition.setRequired(false);

		NumberField floorPosition = new NumberField("floor position");
		floorPosition.setRequired(false);

		binder = new Binder<>(RestaurantTable.class);
		binder.forField(name).asRequired("Name is required").bind(RestaurantTable::getName, RestaurantTable::setName)
			  .setAsRequiredEnabled(true);
		binder
				.forField(capacity)
				.asRequired("Capacity is required")
				.bind(RestaurantTable::getCapacity, RestaurantTable::setCapacity)
				.setAsRequiredEnabled(true);
		binder.forField(xPosition).bind(RestaurantTable::getXPosition, RestaurantTable::setXPosition);
		binder.forField(yPosition).bind(RestaurantTable::getYPosition, RestaurantTable::setYPosition);
		binder.forField(floorPosition).bind(RestaurantTable::getFloorPosition, RestaurantTable::setFloorPosition);

		binder.readBean(restaurantTable);
		FormLayout formLayout = new FormLayout(name, capacity, xPosition, yPosition, floorPosition);
		add(formLayout);
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
				binder.writeBean(restaurantTable);

				if (restaurantTable.getId() == null) {
					restaurantTable.setRestaurant(this.restaurant);
				}
				restaurantTableService.create(restaurantTable);
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
