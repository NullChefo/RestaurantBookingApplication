package com.nullchefo.restaurantbookings.views.menu;

import com.nullchefo.restaurantbookings.entity.Restaurant;
import com.nullchefo.restaurantbookings.service.MenuService;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;

public class MenuDialog extends Dialog {

	private final MenuService menuService;

	private final Restaurant restaurant;

	public MenuDialog(MenuService menuService, Restaurant restaurant) {
		this.menuService = menuService;
		this.restaurant = restaurant;
		add(new H1("To be implemented"));
	}

}
