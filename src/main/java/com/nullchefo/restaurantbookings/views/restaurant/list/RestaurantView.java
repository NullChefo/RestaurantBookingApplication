package com.nullchefo.restaurantbookings.views.restaurant.list;

import org.springframework.beans.factory.annotation.Autowired;

import com.nullchefo.restaurantbookings.configuration.security.AuthenticatedUser;
import com.nullchefo.restaurantbookings.entity.Restaurant;
import com.nullchefo.restaurantbookings.entity.User;
import com.nullchefo.restaurantbookings.entity.enums.RoleEnum;
import com.nullchefo.restaurantbookings.service.LocationService;
import com.nullchefo.restaurantbookings.service.RestaurantService;
import com.nullchefo.restaurantbookings.views.MainLayout;
import com.vaadin.flow.component.accordion.Accordion;
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
@Route(value = "restaurant-v2", layout = MainLayout.class)
@PermitAll
public class RestaurantView extends VerticalLayout {

	private final AuthenticatedUser authenticatedUser;
	private final LocationService locationService;
	private RestaurantService restaurantService;
	private Grid<Restaurant> restaurantGrid;
	private Button editRestaurantButton;
	private Button removeRestaurantButton;

	@Autowired
	public RestaurantView(
			AuthenticatedUser authenticatedUser, RestaurantService restaurantService,
			LocationService locationService) {
		this.authenticatedUser = authenticatedUser;
		this.restaurantService = restaurantService;
		this.locationService = locationService;
		initContent();
	}

	private void initContent() {

		HorizontalLayout elevatedRightsButton = new HorizontalLayout(
				createAddButton(),
				createEditButton(),
				createRemoveButton());
		HorizontalLayout standardRightsButton = new HorizontalLayout(
				createReserveTableButton(),
				createOrderFoodButton(),
				createSeeMenuButton());

		if (this.authenticatedUser.get().isPresent()) {
			if (this.authenticatedUser.get().get().getRoles().contains(RoleEnum.ADMIN) || this.authenticatedUser
					.get()
					.get()
					.getRoles()
					.contains(RoleEnum.ORGANISATION)) {
				add(elevatedRightsButton);
			}
		} else {
			add(standardRightsButton);
		}

		add(createRestaurantGrid());
		add(createRestaurantGrid());
		Accordion accordion = new Accordion();
		accordion.setSizeFull();

		add(accordion);
		setSizeFull();

	}

	private Button createReserveTableButton() {
		Button reserveTableButton = new Button("Reserve table");
		reserveTableButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		reserveTableButton.setTooltipText("Reserve table");
		reserveTableButton.addClickListener(l -> openReserveTableDialog(new Restaurant(), this.authenticatedUser.get()
																												.orElse(null)));
		return reserveTableButton;
	}

	private Button createOrderFoodButton() {
		Button addRestaurantButton = new Button("Order food");
		addRestaurantButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		addRestaurantButton.setTooltipText("Order food");
		addRestaurantButton.addClickListener(l -> openCreateOrderDialog(new Restaurant(), this.authenticatedUser.get()
																												.orElse(null)));
		return addRestaurantButton;
	}

	private Button createSeeMenuButton() {
		Button addSeeMenuButton = new Button("See menu");
		addSeeMenuButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		addSeeMenuButton.setTooltipText("See menu");
		addSeeMenuButton.addClickListener(l -> openMenuDialog(new Restaurant(), this.authenticatedUser.get()
																									  .orElse(null)));
		return addSeeMenuButton;
	}

	private Grid<Restaurant> createRestaurantGrid() {
		restaurantGrid = new RestaurantGrid();
		restaurantGrid.setMinHeight("300px");
		restaurantGrid.addItemDoubleClickListener(l -> openRestaurantGrid(l.getItem(), this.authenticatedUser.get()
																											 .orElse(null)));
		SingleSelect<Grid<Restaurant>, Restaurant> singleSelect = restaurantGrid.asSingleSelect();
		singleSelect.addValueChangeListener(l -> {
			Restaurant value = l.getValue();
			boolean enabled = value != null;
			editRestaurantButton.setEnabled(enabled);
			removeRestaurantButton.setEnabled(enabled);
			//            reloadSubRestaurantGridData();
		});
		reloadGrid();
		return restaurantGrid;
	}

	//    private Grid<Restaurant> createChildrenGrid() {
	//        restaurantChildren = new RestaurantGrid();
	//        restaurantChildren.setMinHeight("200px");
	//        restaurantChildren.setSelectionMode(Grid.SelectionMode.MULTI);
	//        MultiSelect<Grid<Restaurant>, Restaurant> multiSelect = restaurantChildren.asMultiSelect();
	//        multiSelect.addSelectionListener(l -> {
	//            Set<Restaurant> categories = l.getValue();
	//            removeSubRestaurant.setEnabled(!categories.isEmpty());
	//            deleteSubRestaurant.setEnabled(categories.size() == 1);
	//        });
	//
	//        return restaurantChildren;
	//    }

	private Button createRemoveButton() {
		removeRestaurantButton = new Button("Remove");
		removeRestaurantButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		removeRestaurantButton.setEnabled(false);
		removeRestaurantButton.setTooltipText("Изтрий Категория");
		removeRestaurantButton.addClickListener(l -> {
			Dialog dialog = new Dialog();
			Restaurant value = restaurantGrid.asSingleSelect().getValue();
			dialog.setHeaderTitle("Are you sure you want to delete restaurant with name: " + value.getName());
			Button ок = new Button("Yes", ll -> {
				restaurantService.delete(value.getId());
				reloadGrid();
				dialog.close();
			});
			Button no = new Button("No", ll -> dialog.close());
			dialog.getFooter().add(ок, no);
			dialog.open();
		});
		return removeRestaurantButton;
	}

	private Button createEditButton() {
		editRestaurantButton = new Button("Edit");
		editRestaurantButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		editRestaurantButton.addClickListener(l -> openRestaurantGrid(
				restaurantGrid.asSingleSelect().getValue(),
				this.authenticatedUser
						.get()
						.orElse(null)));
		editRestaurantButton.setEnabled(false);
		editRestaurantButton.setTooltipText("Change Restaurant");
		return editRestaurantButton;
	}

	private void openRestaurantGrid(Restaurant restaurant, User user) {
		RestaurantDialog dialog = new RestaurantDialog(restaurant, restaurantService, locationService, user);
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
		addRestaurantButton.addClickListener(l -> openRestaurantGrid(new Restaurant(), this.authenticatedUser.get()
																											 .orElse(null)));
		return addRestaurantButton;
	}

	private void reloadGrid() {
		restaurantGrid.deselectAll();
		restaurantGrid.setItems(restaurantService.findAll());
		//        reloadSubRestaurantGridData();
	}
}
