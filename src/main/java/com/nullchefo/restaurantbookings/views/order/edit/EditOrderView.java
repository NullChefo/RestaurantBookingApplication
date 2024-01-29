package com.nullchefo.restaurantbookings.views.order.edit;

import com.nullchefo.restaurantbookings.views.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@PageTitle("Create order")
@Route(value = "order/edit", layout = MainLayout.class)
@PermitAll
public class EditOrderView extends VerticalLayout {
	public EditOrderView() {
	}
}
