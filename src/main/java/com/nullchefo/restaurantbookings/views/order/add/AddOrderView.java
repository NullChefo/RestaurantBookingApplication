package com.nullchefo.restaurantbookings.views.order.add;

import com.nullchefo.restaurantbookings.views.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@PageTitle("Create order")
@Route(value = "order/add", layout = MainLayout.class)
@PermitAll
public class AddOrderView extends VerticalLayout {
	public AddOrderView() {
	}
}
