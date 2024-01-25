package com.nullchefo.restaurantbookings.views.informational.notFound;

import com.nullchefo.restaurantbookings.views.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("404")
@RouteAlias(value = "not-found", layout = MainLayout.class)
@AnonymousAllowed
public class NotFoundView extends FlexLayout {

	public NotFoundView() {
		setSizeFull();
		setJustifyContentMode(JustifyContentMode.CENTER);
		setAlignItems(Alignment.CENTER);
		setMinHeight("93vh");

		H1 errorMessage = new H1("404");
		errorMessage.getStyle().set("font-size", "9rem");

		add(errorMessage);
	}
}
