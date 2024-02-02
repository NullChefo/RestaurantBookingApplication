package com.nullchefo.restaurantbookings.views.restaurant;

import org.springframework.data.domain.PageRequest;

import com.nullchefo.restaurantbookings.entity.Location;
import com.nullchefo.restaurantbookings.entity.Restaurant;
import com.nullchefo.restaurantbookings.entity.RestaurantTable;
import com.nullchefo.restaurantbookings.entity.User;
import com.nullchefo.restaurantbookings.entity.enums.EntityStatus;
import com.nullchefo.restaurantbookings.service.LocationService;
import com.nullchefo.restaurantbookings.service.RestaurantService;
import com.nullchefo.restaurantbookings.service.RestaurantTableService;
import com.nullchefo.restaurantbookings.views.location.LocationDialog;
import com.nullchefo.restaurantbookings.views.restaurantTable.RestaurantTableDialog;
import com.nullchefo.restaurantbookings.views.restaurantTable.RestaurantTableGrid;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;

public class RestaurantDialog extends Dialog {

	private final Restaurant restaurant;
	private final RestaurantService restaurantService;
	private final LocationService locationService;
	private final User user;
	private Button saveButton;

	private RestaurantTableGrid restaurantTableGrid;

	private final RestaurantTableService restaurantTableService;

	private Button addLocationButton;
	private Button addTablesButton;
	private Button removeTablesButton;

	private Button editTablesButton;
	private Binder<Restaurant> binder;
	private ComboBox<Location> locationComboBox;



	public RestaurantDialog(
			Restaurant restaurant, RestaurantService restaurantService,
			LocationService locationService, User user, RestaurantTableService restaurantTableService) {
		this.restaurant = restaurant;
		this.restaurantService = restaurantService;
		this.locationService = locationService;
		this.user = user;
		this.restaurantTableService = restaurantTableService;
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

		// always active
		this.addLocationButton = new Button("Add/Edit Location");
		this.addLocationButton.addClickListener(e -> {
			LocationDialog dialog = new LocationDialog(this.locationComboBox.getValue(), locationService, user);
			dialog.addSaveClickListener(buttonClickEvent -> {
				loadLocationComboBox();
				dialog.close();
			});
			dialog.open();
		});


		// always active
		this.addTablesButton = new Button("Add Table");
		this.addTablesButton.addClickListener(e -> {
			RestaurantTableDialog dialog = new RestaurantTableDialog(new RestaurantTable(), this.restaurantTableService, this.restaurant);
			dialog.addSaveClickListener(buttonClickEvent -> {
				loadRestaurantTableGrid();
				dialog.close();
			});
			dialog.open();
		});

		this.editTablesButton = new Button("Edit Table");
		this.editTablesButton.setEnabled(false);
		this.editTablesButton.addClickListener(e -> {
			RestaurantTableDialog dialog = new RestaurantTableDialog(this.restaurantTableGrid.asSingleSelect().getValue(), this.restaurantTableService, this.restaurant);
			dialog.addSaveClickListener(buttonClickEvent -> {
				loadRestaurantTableGrid();
				dialog.close();
			});
			dialog.open();
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
		locationLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
		locationLayout.add(this.locationComboBox, this.addLocationButton );

		FormLayout formLayout = new FormLayout(name, pictureURL, locationLayout);
		add(formLayout);

		HorizontalLayout restaurantActionButtonsLayout = new HorizontalLayout();
		restaurantActionButtonsLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
		restaurantActionButtonsLayout.add(this.addTablesButton, this.editTablesButton, createRemoveRestaurantTableButton());
		add(restaurantActionButtonsLayout);
		add(createRestaurantTableGrid());

		loadRestaurantTableGrid();


	}

	private void loadLocationComboBox() {
		this.locationComboBox.setItems(locationService.findAllByUser(this.user));
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
				binder.writeBean(restaurant);
				if (restaurant.getId() == null) {
					restaurant.setOwner(this.user);
				}

				if (restaurant.getEntityStatus() != null) {
					if (restaurant.getEntityStatus() == EntityStatus.TEMPORARY) {
						restaurant.setEntityStatus(EntityStatus.ACTIVE);
					}
				}
				restaurantService.create(restaurant);


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


	private Button createRemoveRestaurantTableButton() {
		removeTablesButton = new Button("Remove");
		removeTablesButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		removeTablesButton.setEnabled(false);
		removeTablesButton.setTooltipText("Remove restaurant");
		removeTablesButton.addClickListener(l -> {
			Dialog dialog = new Dialog();
			RestaurantTable value = this.restaurantTableGrid.asSingleSelect().getValue();
			dialog.setHeaderTitle("Are you sure you want to delete table with name: " + value.getName());
			Button ok = new Button("Yes", ll -> {
				restaurantTableService.delete(value.getId());
				loadRestaurantTableGrid();
				dialog.close();
			});
			Button no = new Button("No", ll -> dialog.close());
			dialog.getFooter().add(ok, no);
			dialog.open();
		});
		return removeTablesButton;
	}

	private Grid<RestaurantTable> createRestaurantTableGrid() {
		restaurantTableGrid = new RestaurantTableGrid();
		restaurantTableGrid.setMinHeight("300px");

		restaurantTableGrid.addItemDoubleClickListener(l -> openRestaurantTableDialog(l.getItem(),this.restaurant));

		SingleSelect<Grid<RestaurantTable>, RestaurantTable> singleSelect = restaurantTableGrid.asSingleSelect();
		singleSelect.addValueChangeListener(l -> {
			RestaurantTable value = l.getValue();
			boolean enabled = value != null;
			editTablesButton.setEnabled(enabled);
			removeTablesButton.setEnabled(enabled);

		});
		loadRestaurantTableGrid();
		return restaurantTableGrid;
	}

	private void openRestaurantTableDialog(final RestaurantTable item, final Restaurant restaurant) {
		RestaurantTableDialog dialog = new RestaurantTableDialog(item, this.restaurantTableService, restaurant);
		dialog.addSaveClickListener(buttonClickEvent -> {
			loadRestaurantTableGrid();
			dialog.close();
		});
		dialog.open();
	}

	private void loadRestaurantTableGrid() {
		this.restaurantTableGrid.deselectAll();
		this.restaurantTableGrid.setItems(restaurantTableService.findAllByRestaurant(this.restaurant));
	}

}
