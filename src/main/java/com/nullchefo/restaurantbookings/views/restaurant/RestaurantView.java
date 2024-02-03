package com.nullchefo.restaurantbookings.views.restaurant;

import org.springframework.beans.factory.annotation.Autowired;

import com.nullchefo.restaurantbookings.configuration.security.AuthenticatedUser;
import com.nullchefo.restaurantbookings.entity.Restaurant;
import com.nullchefo.restaurantbookings.entity.User;
import com.nullchefo.restaurantbookings.entity.enums.EntityStatus;
import com.nullchefo.restaurantbookings.entity.enums.RoleEnum;
import com.nullchefo.restaurantbookings.service.LocationService;
import com.nullchefo.restaurantbookings.service.OrderService;
import com.nullchefo.restaurantbookings.service.ReservationService;
import com.nullchefo.restaurantbookings.service.RestaurantService;
import com.nullchefo.restaurantbookings.service.RestaurantTableService;
import com.nullchefo.restaurantbookings.views.MainLayout;
import com.nullchefo.restaurantbookings.views.reservation.ReserveTableDialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@PageTitle("Restaurant")
@Route(value = "restaurant", layout = MainLayout.class)
@PermitAll
public class RestaurantView extends VerticalLayout {

	private final AuthenticatedUser authenticatedUser;
	private final LocationService locationService;

	private final OrderService orderService;

	private final RestaurantTableService restaurantTableService;
	private final ReservationService reservationService;
	private final RestaurantService restaurantService;
	private final User user;
	private Grid<Restaurant> restaurantGrid;
	private Button editRestaurantButton;
	private Button removeRestaurantButton;
	//	private Button orderFoodButton;
	private Button reserveTableButton;

	@Autowired
	public RestaurantView(
			AuthenticatedUser authenticatedUser, RestaurantService restaurantService,
			LocationService locationService, OrderService orderService, RestaurantTableService restaurantTableService,
			ReservationService reservationService) {
		this.authenticatedUser = authenticatedUser;
		this.restaurantService = restaurantService;
		this.locationService = locationService;
		this.orderService = orderService;
		this.restaurantTableService = restaurantTableService;
		this.reservationService = reservationService;

		this.user = this.authenticatedUser.get().orElseThrow(() -> new RuntimeException("User should be logged in"));
		initContent();
	}

	private void initContent() {

		HorizontalLayout elevatedRightsButton = new HorizontalLayout(
				createAddButton(),
				createEditButton(),
				createRemoveButton());
		HorizontalLayout standardRightsButton = new HorizontalLayout(
				createReserveTableButton()
				//			,createOrderFoodButton()
		);

		if (this.isUserLoggedInAndHaveElevatedRights()) {
			add(elevatedRightsButton);
		} else {
			add(standardRightsButton);
		}

		add(createRestaurantGrid());

		setSizeFull();

	}

	private Button createReserveTableButton() {
		this.reserveTableButton = new Button("Reserve table");
		this.reserveTableButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		this.reserveTableButton.setTooltipText("Reserve table");
		this.reserveTableButton.addClickListener(l -> openReserveTableDialog(
				restaurantGrid.asSingleSelect().getValue(),
				this.user));
		this.reserveTableButton.setEnabled(false);
		return this.reserveTableButton;
	}

	private void openReserveTableDialog(final Restaurant restaurant, final User user) {
		ReserveTableDialog restaurantTableDialog = new ReserveTableDialog(
				user,
				restaurant,
				restaurantTableService,
				restaurantService,
				this.reservationService);
		restaurantTableDialog.addSaveClickListener(ll -> {
			//			reloadGrid();
			restaurantTableDialog.close();
		});
		restaurantTableDialog.open();
	}

	//	private Button createOrderFoodButton() {
	//		this.orderFoodButton = new Button("Order food");
	//		this.orderFoodButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
	//		this.orderFoodButton.setTooltipText("Order food");
	//		this.orderFoodButton.addClickListener(l -> openCreateOrderDialog(restaurantGrid.asSingleSelect().getValue(), this.authenticatedUser.get()
	//																												.orElse(null)));
	//		return this.orderFoodButton;
	//	}
	//
	//	private void openCreateOrderDialog(final Restaurant restaurant, final User user) {
	//		OrderDialog orderDialog = new OrderDialog(this.orderService, restaurant, user);
	//		orderDialog.addSaveClickListener(ll -> {
	////			reloadGrid();
	//			menuDialog.close();
	//		});
	//		orderDialog.open();
	//	}

	private Grid<Restaurant> createRestaurantGrid() {
		restaurantGrid = new RestaurantGrid();
		restaurantGrid.setMinHeight("300px");
		if (isUserLoggedInAndHaveElevatedRights()) {
			restaurantGrid.addItemDoubleClickListener(l -> openRestaurantDialog(l.getItem(), this.user));
		} else {
			restaurantGrid.addItemDoubleClickListener(l -> openReserveTableDialog(l.getItem(), this.user));
		}

		SingleSelect<Grid<Restaurant>, Restaurant> singleSelect = restaurantGrid.asSingleSelect();
		singleSelect.addValueChangeListener(l -> {
			Restaurant value = l.getValue();
			boolean enabled = value != null;
			if (this.isUserLoggedInAndHaveElevatedRights()) {
				editRestaurantButton.setEnabled(enabled);
				removeRestaurantButton.setEnabled(enabled);
				//            reloadSubRestaurantGridData();
			} else {
				reserveTableButton.setEnabled(enabled);
				//				orderFoodButton.setEnabled(enabled);
			}
		});
		reloadGrid();
		return restaurantGrid;
	}

	private Button createRemoveButton() {
		removeRestaurantButton = new Button("Remove");
		removeRestaurantButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		removeRestaurantButton.setEnabled(false);
		removeRestaurantButton.setTooltipText("Remove restaurant");
		removeRestaurantButton.addClickListener(l -> {
			Dialog dialog = new Dialog();
			Restaurant value = restaurantGrid.asSingleSelect().getValue();
			dialog.setHeaderTitle("Are you sure you want to delete restaurant with name: " + value.getName());
			Button ok = new Button("Yes", ll -> {
				restaurantService.delete(value.getId());
				reloadGrid();
				dialog.close();
			});
			Button no = new Button("No", ll -> dialog.close());
			dialog.getFooter().add(ok, no);
			dialog.open();
		});
		return removeRestaurantButton;
	}

	private Button createEditButton() {
		this.editRestaurantButton = new Button("Edit");
		this.editRestaurantButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		this.editRestaurantButton.addClickListener(l -> openRestaurantDialog(
				restaurantGrid.asSingleSelect().getValue(),
				this.user));
		this.editRestaurantButton.setEnabled(false);
		this.editRestaurantButton.setTooltipText("Edit Restaurant");
		return this.editRestaurantButton;
	}

	// 
	private void openRestaurantDialog(Restaurant restaurant, User user) {
		RestaurantDialog dialog = new RestaurantDialog(
				restaurant,
				this.restaurantService,
				this.locationService,
				user,
				this.restaurantTableService);
		dialog.addSaveClickListener(ll -> {
			reloadGrid();
			dialog.close();
		});
		dialog.open();
	}

	private Button createAddButton() {
		Button addRestaurantButton = new Button("Add");
		addRestaurantButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		addRestaurantButton.setTooltipText("Add Restaurant");
		addRestaurantButton.addClickListener(l -> openRestaurantDialog(this.getTempRestaurantRecord(), this.user));
		return addRestaurantButton;
	}

	private Restaurant getTempRestaurantRecord() {
		Restaurant tempRestaurant = this.restaurantService.findByUserAndCreationStatus(
				this.user,
				EntityStatus.TEMPORARY);

		if (tempRestaurant == null) {
			tempRestaurant = new Restaurant();
			tempRestaurant.setOwner(this.user);
			tempRestaurant.setEntityStatus(EntityStatus.TEMPORARY);
			tempRestaurant = this.restaurantService.create(tempRestaurant);
		}
		return tempRestaurant;
	}

	private void reloadGrid() {
		restaurantGrid.deselectAll();

		if (isUserLoggedInAndItIsOrganization()) {
			restaurantGrid.setItems(restaurantService.findAllByOwner(this.user));
		} else {
			restaurantGrid.setItems(restaurantService.findAll());

		}
	}

	private boolean isUserLoggedInAndHaveElevatedRights() {
		return this.user.getRoles().contains(RoleEnum.ADMIN) || this.user.getRoles().contains(RoleEnum.ORGANISATION);
	}

	private boolean isUserLoggedInAndItIsOrganization() {
		return this.user.getRoles().contains(RoleEnum.ORGANISATION);
	}

}
