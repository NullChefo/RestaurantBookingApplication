package com.nullchefo.restaurantbookings.views.order;

import com.nullchefo.restaurantbookings.entity.Restaurant;
import com.nullchefo.restaurantbookings.entity.User;
import com.nullchefo.restaurantbookings.service.OrderService;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;

public class OrderDialog extends Dialog {

	private final OrderService orderService;

	private final Restaurant restaurant;

	private final User user;

	public OrderDialog(OrderService orderService, Restaurant restaurant, User user) {
		this.orderService = orderService;
		this.restaurant = restaurant;
		this.user = user;
		add(new H1("To be implemented"));
	}
}
