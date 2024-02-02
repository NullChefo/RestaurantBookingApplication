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
package com.nullchefo.restaurantbookings.views.user.listUsers;

import java.util.List;
import java.util.Set;

import com.nullchefo.restaurantbookings.entity.Restaurant;
import com.nullchefo.restaurantbookings.entity.User;
import com.nullchefo.restaurantbookings.service.UserService;
import com.nullchefo.restaurantbookings.views.MainLayout;
import com.nullchefo.restaurantbookings.views.user.editUser.EditUserDialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@PageTitle("List users")
@Route(value = "user", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class ListUserView extends VerticalLayout {
	private final UserService userService;
	private final int itemsPerPage = 10;
	//	private ListDataProvider<User> dataProvider;
	private final Grid<User> grid;
	private long totalAmountOfPages;
	private int currentPageNumber = 1;
	private Button editUserButton;
	private Button removeUserButton;

	public ListUserView(UserService userService) {
		this.userService = userService;





		this.grid = new Grid<>(User.class);
		totalAmountOfPages = calculateTotalPages(this.userService.getUserCount(), itemsPerPage);
		List<User> initialItems = this.userService.getUsers(currentPageNumber, itemsPerPage);
		refreshDataToGrid(initialItems);
		//		grid.setItems(initialItems);

		// Customize visible columns
		grid.setColumns("username", "phone", "email");

		grid.addColumn(user -> user.getFirstName() + " " + user.getLastName()).setHeader("name");
		//		grid.addColumn(User::getEmail).setHeader("email");
		//		grid.addColumn(User::getPhone).setHeader("phone");
		grid.addColumn(User::getRoles).setHeader("roles");
		grid.addColumn(User::getRoles).setHeader("roles");

		SingleSelect<Grid<User>, User> singleSelect = grid.asSingleSelect();
		singleSelect.addValueChangeListener(l -> {
			User value = l.getValue();
			boolean enabled = value != null;

				editUserButton.setEnabled(enabled);
				removeUserButton.setEnabled(enabled);

		});

		HorizontalLayout searchLayout = getHorizontalLayout(initialItems);
		searchLayout.add(addEditUserButton(),createRemoveButton() );

		HorizontalLayout buttonLayout = getHorizontalLayout(grid);
		add(searchLayout, grid, buttonLayout);
	}


	private Button createRemoveButton() {
		this.removeUserButton = new Button("Remove");
		this.removeUserButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		this.removeUserButton.setEnabled(false);
		this.removeUserButton.setTooltipText("Remove user");
		this.removeUserButton.addClickListener(l -> {
			Dialog dialog = new Dialog();
			User value = grid.asSingleSelect().getValue();
			dialog.setHeaderTitle("Are you sure you want to delete user with username: " + value.getUsername());
			Button ok = new Button("Yes", ll -> {
				userService.delete(value.getId());
				reloadGrid();
				dialog.close();
			});
			Button no = new Button("No", ll -> dialog.close());
			dialog.getFooter().add(ok, no);
			dialog.open();
		});
		return this.removeUserButton;
	}

	private Button addEditUserButton(){
		this.editUserButton = new Button("Edit");
		this.editUserButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		this.editUserButton.setTooltipText("Edit user");
		this.editUserButton.addClickListener(l -> openEditUserDialog(grid.asSingleSelect().getValue()));
		return this.editUserButton;
	}

	private void openEditUserDialog(final User selectedUser) {
		EditUserDialog editUserDialog = new EditUserDialog(this.userService, selectedUser);
		editUserDialog.addSaveClickListener(ll -> {
			reloadGrid();
			editUserDialog.close();
		});
		editUserDialog.open();

	}

	private void reloadGrid() {
		this.grid.setItems(this.userService.getUsers(currentPageNumber, itemsPerPage));
	}

	private HorizontalLayout getHorizontalLayout(final List<User> initialItems) {
		TextField searchField = new TextField("Search by Name");
		searchField.addValueChangeListener(event -> {
			String searchTerm = event.getValue().toLowerCase();
			List<User> searchResults = this.userService.searchUsersByUsername(searchTerm);
			refreshDataToGrid(searchResults);
		});

		// Add a Button to clear the search
		Button clearSearchButton = new Button("Clear Search", event -> {
			searchField.clear();
			refreshDataToGrid(initialItems);
		});

		HorizontalLayout searchLayout = new HorizontalLayout(searchField, clearSearchButton);
		searchLayout.setAlignItems(Alignment.END);
		return searchLayout;
	}

	private void refreshDataToGrid(final List<User> list) {
		//		this.dataProvider = new ListDataProvider<>(list);
		//		this.grid.setDataProvider(this.dataProvider);
		this.grid.setItems(list);
	}

	private HorizontalLayout getHorizontalLayout(final Grid<User> grid) {
		HorizontalLayout buttonLayout = new HorizontalLayout();

		Button nextButton = new Button("Next page", e -> {
			if (currentPageNumber >= totalAmountOfPages) {
				return;
			}
			List<User> nextPageItems = this.userService.getUsers(++currentPageNumber, itemsPerPage);
			grid.setItems(nextPageItems);
		});

		Button previousButton = new Button("Previous page", e -> {
			if (currentPageNumber <= 1) {
				return;
			}
			List<User> prevPageItems = this.userService.getUsers(--currentPageNumber, itemsPerPage);
			grid.setItems(prevPageItems);
		});

		buttonLayout.add(previousButton, nextButton);
		return buttonLayout;
	}

	public long calculateTotalPages(Long totalCount, int itemsPerPage) {
		if (totalCount <= 0 || itemsPerPage <= 0) {
			return 0;
		}

		return (long) Math.ceil((double) totalCount / itemsPerPage);
	}

}
