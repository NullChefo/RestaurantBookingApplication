/*
 * Copyright 2024 Stefan Kehayov
 *
 * All rights reserved. Unauthorized use, reproduction, or distribution
 * of this software, or any portion of it, is strictly prohibited.
 *
 * The software is provided "as is", without warranty of any kind,
 * express or implied, including but not limited to the warranties
 * of merchantability, fitness for a particular purpose, and noninfringement.
 * In no event shall the authors or copyright holders be liable for any claim,
 * damages, or other liability, whether in an action of contract, tort, or otherwise,
 * arising from, out of, or in connection with the software or the use or other dealings
 * in the software.
 *
 * Usage of this software by corporations, for machine learning, or AI purposes
 * is expressly prohibited.
 */
package com.nullchefo.restaurantbookings.views.restaurant;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

//@PageTitle("Restaurants")
//@Route(value = "restaurant", layout = MainLayout.class)
//@PermitAll
public class RestaurantListView extends VerticalLayout {

	//	private final Grid<Restaurant> grid = new Grid<>(Restaurant.class);
	//	private final AuthenticatedUser authenticatedUser;
	//	private HorizontalLayout buttonsLayout = new HorizontalLayout();
	//	private AddReservationView addReservationView = new AddReservationView();
	//
	//	private final RestaurantService restaurantService;
	//
	//	private List<Restaurant> restaurants;
	//
	//
	//	@Autowired
	//	public RestaurantListView(final AuthenticatedUser authenticatedUser,
	//							  RestaurantService restaurantService) {
	//		this.authenticatedUser = authenticatedUser;
	//		this.restaurantService = restaurantService;
	//
	//		Optional<User> maybeUser = this.authenticatedUser.get();
	//
	//		if (maybeUser.isPresent()) {
	//			if(maybeUser.get().getRoles().contains(RoleEnum.ORGANISATION) || maybeUser.get().getRoles().contains(RoleEnum.ADMIN)) {
	//				Button addButton = new Button("Add");
	//				addButton.addClickListener(event -> {
	////					showDialog(addOrEditReservationView);
	//				});
	//				buttonsLayout.add(addButton);
	//
	//				Button editRestaurantButton = new Button("Edit");
	//				editRestaurantButton.addClickListener(event -> {
	////					showDialog(addOrEditReservationView);
	//				});
	//				buttonsLayout.add(editRestaurantButton);
	//
	//				Button deleteRestaurantButton = new Button("Delete");
	//				editRestaurantButton.addClickListener(event -> {
	////					restaurantService.delete()
	//				});
	//				buttonsLayout.add(editRestaurantButton);
	//
	//
	//
	//			}
	//
	//			if(maybeUser.get().getRoles().contains(RoleEnum.CUSTOMER)) {
	//				// todo if item from table selected
	//				Button viewMenu = new Button("View menu");
	//				viewMenu.addClickListener(event -> {
	//					showDialog(addReservationView);
	//				});
	//				buttonsLayout.add(viewMenu);
	//
	//
	//				Button reserveTable = new Button("Reserve Table");
	//				reserveTable.addClickListener(event -> {
	//					showDialog(addReservationView);
	//				});
	//				buttonsLayout.add(reserveTable);
	//
	//				Button orderFood = new Button("Order food");
	//				orderFood.addClickListener(event -> {
	//					showDialog(addReservationView);
	//				});
	//				buttonsLayout.add(orderFood);
	//
	//			}
	//
	//		}
	//
	//		restaurants = restaurantService.findAll();
	//
	//		grid.setItems(restaurants);
	//		// set them from entity
	//		grid.setColumns(
	//				"name"
	//			);
	//
	////		grid.addColumn(Restaurant::getName).setHeader("name");
	//		grid.addColumn(restaurant -> restaurant.getOwner().getFirstName() + " " + restaurant.getOwner().getLastName()).setHeader("owner");
	//		grid.addColumn(restaurant -> restaurant.getLocation().getAddress()).setHeader("location");
	//		grid.addColumn(Restaurant::getPictureURL).setHeader("picture");
	//
	//		SingleSelect<Grid<Restaurant>, Restaurant> singleSelect = grid.asSingleSelect();
	//		singleSelect.addValueChangeListener(l -> {
	//			Restaurant value = l.getValue();
	//			boolean enabled = value != null;
	////			editCategoryButton.setEnabled(enabled);
	////			removeCategoryButton.setEnabled(enabled);
	//		});
	//
	//		add(buttonsLayout,grid);
	//
	//	}
	//	private void showDialog(Component view) {
	//		Dialog dialog = new Dialog();
	//		dialog.add(view);
	//		dialog.setMaxWidth("70vw");
	//		dialog.setMaxHeight("90vh");
	//		dialog.open();
	//	}
	//
	//	private void showActionButtons(){
	//		HorizontalLayout horizontalLayout = new HorizontalLayout();
	//		Button addButton = new Button("Edit");

	//	}

	//	private void getGrid(List<Restaurant> restaurants) {
	//
	//		Grid<Restaurant> grid = new Grid<>(Restaurant.class, false);
	//		grid.setItems(restaurants);
	//		grid.addColumn(createAvatarRenderer()).setHeader("Image")
	//			.setAutoWidth(true).setFlexGrow(0);
	//		grid.addColumn(Person::getFirstName).setHeader("First name");
	//		grid.addColumn(Person::getLastName).setHeader("Last name");
	//		grid.addColumn(GridWrapCellContent::formatAddress).setHeader("Address");
	//		grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
	//
	//	}
}
