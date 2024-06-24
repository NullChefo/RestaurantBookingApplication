package com.nullchefo.restaurantbookings.views.order.list;

import com.nullchefo.restaurantbookings.views.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@PageTitle("Create order")
@Route(value = "order/list", layout = MainLayout.class)
@PermitAll
public class ListOrderView extends VerticalLayout {

	public ListOrderView() {
		add(new H1("To be implemented"));
	}
}
