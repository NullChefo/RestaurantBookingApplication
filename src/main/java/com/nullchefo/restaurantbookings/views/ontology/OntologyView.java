package com.nullchefo.restaurantbookings.views.ontology;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.nullchefo.restaurantbookings.configuration.security.AuthenticatedUser;
import com.nullchefo.restaurantbookings.entity.Dish;
import com.nullchefo.restaurantbookings.entity.Restaurant;
import com.nullchefo.restaurantbookings.entity.User;
import com.nullchefo.restaurantbookings.entity.enums.EntityStatus;
import com.nullchefo.restaurantbookings.entity.enums.RoleEnum;
import com.nullchefo.restaurantbookings.service.LocationService;
import com.nullchefo.restaurantbookings.service.OntologyManagerService;
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
import com.vaadin.flow.data.selection.MultiSelect;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@PageTitle("Ontology")
@Route(value = "ontology", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class OntologyView extends VerticalLayout {

	private final AuthenticatedUser authenticatedUser;
	private final User user;
	private DishGrid dishGrid;

	private Button editDishButton;
	private Button removeDishButton;



	private final OntologyManagerService ontologyManagerService;

	public OntologyView(final AuthenticatedUser authenticatedUser, final OntologyManagerService ontologyManagerService) {
		this.authenticatedUser = authenticatedUser;
		this.ontologyManagerService = ontologyManagerService;
		this.user = this.authenticatedUser.get().orElseThrow(() -> new RuntimeException("User should be logged in"));
		initContent();
	}

	private void initContent() {

		HorizontalLayout elevatedRightsButton = new HorizontalLayout(
				createAddButton(),
				createEditButton(),
				createRemoveButton());


		if (this.isUserLoggedInAndHaveElevatedRights()) {
			add(elevatedRightsButton);
		}
//		else {
//			add(standardRightsButton);
//		}

		add(createDishGrid());
		reloadGrid();
		setSizeFull();

	}



	private DishGrid createDishGrid() {
		dishGrid = new DishGrid();

		dishGrid.addItemDoubleClickListener(l -> openDishDialog(l.getItem()));

		// make it to work with multi select

		MultiSelect<Grid<Dish>, Dish> multiSelect = dishGrid.asMultiSelect();
		multiSelect.addSelectionListener(event -> {
			Set<Dish> selectedItems = event.getAllSelectedItems();
			boolean enabled = !selectedItems.isEmpty();

			editDishButton.setEnabled(enabled);
			removeDishButton.setEnabled(enabled);

			 if (selectedItems.size()  > 1) {
				 editDishButton.setEnabled(false);
			 }

			// You can also handle other logic based on the selected items
			// For example, updating a form with details of the selected dishes
			// if (selectedItems.size() == 1) {
			//     Dish selectedDish = selectedItems.iterator().next();
			//     // Update form with details of selectedDish
			// }
		});
		return dishGrid;
	}



	private Button createRemoveButton() {
		removeDishButton = new Button("Remove");
		removeDishButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		removeDishButton.setEnabled(false);
		removeDishButton.setTooltipText("Remove Dish");

		removeDishButton.addClickListener(l -> {
			Set<Dish> selectedDishes = dishGrid.asMultiSelect().getSelectedItems();
			if (selectedDishes.isEmpty()) {
				return; // No items selected
			}

			Dialog dialog = new Dialog();
			StringBuilder dishNames = new StringBuilder();
			for (Dish dish : selectedDishes) {
				dishNames.append(dish.getName()).append(", ");
			}
			// Remove the trailing comma and space
			if (dishNames.length() > 0) {
				dishNames.setLength(dishNames.length() - 2);
			}

			dialog.setHeaderTitle("Are you sure you want to delete the following dishes: " + dishNames.toString() + "?");

			Button ok = new Button("Yes", ll -> {
				for (Dish dish : selectedDishes) {
					ontologyManagerService.deleteDish(dish.getName());
				}
				reloadGrid();
				dialog.close();
			});

			Button no = new Button("No", ll -> dialog.close());
			dialog.getFooter().add(ok, no);
			dialog.open();
		});

		return removeDishButton;
	}

	private Button createEditButton() {
		this.editDishButton = new Button("Edit");
		this.editDishButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		this.editDishButton.addClickListener(l ->
											 {

												 Set<Dish> selectedDishes = dishGrid.asMultiSelect().getSelectedItems();
												 if (selectedDishes.isEmpty()) {
													 return; // No items selected
												 }
												 openDishDialog(selectedDishes.iterator().next());

											 }
																);

		this.editDishButton.setEnabled(false);
		this.editDishButton.setTooltipText("Edit Dish");
		return this.editDishButton;
	}

	//
	private void openDishDialog(final Dish dish) {
		DishDialog dialog = new DishDialog(dish, ontologyManagerService);
		dialog.addSaveClickListener(ll -> {
			reloadGrid();
			dialog.close();
		});
		dialog.open();
	}

	private Button createAddButton() {
		Button addRestaurantButton = new Button("Add");
		addRestaurantButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		addRestaurantButton.setTooltipText("Add Dish");
		addRestaurantButton.addClickListener(l -> openDishDialog(new Dish()));
		return addRestaurantButton;
	}



	private void reloadGrid() {
		dishGrid.deselectAll();
		dishGrid.setItems(ontologyManagerService.getAllDishes());
	}

	private boolean isUserLoggedInAndHaveElevatedRights() {
		return this.user.getRoles().contains(RoleEnum.ADMIN) || this.user.getRoles().contains(RoleEnum.ORGANISATION);
	}

	private boolean isUserLoggedInAndItIsOrganization() {
		return this.user.getRoles().contains(RoleEnum.ORGANISATION);
	}

}
