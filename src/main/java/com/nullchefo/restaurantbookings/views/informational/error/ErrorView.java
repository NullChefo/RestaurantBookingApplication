package com.nullchefo.restaurantbookings.views.informational.error;

import com.nullchefo.restaurantbookings.views.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Error")
@Route(value = "error", layout = MainLayout.class)
@AnonymousAllowed
public class ErrorView  extends VerticalLayout {
	public ErrorView() {
		add(new H1("Error"));
	}

}
