package com.nullchefo.restaurantbookings.views.informational.home;

import com.nullchefo.restaurantbookings.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Home")
@Route(value = "home", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@AnonymousAllowed
public class HomeView extends VerticalLayout {

	// add marketing shit
	public HomeView() {
		setPadding(true);
		setMargin(true);
		setSpacing(true);
		// Header with inline CSS
		H1 title = new H1("Savor Moments, Delight in Every Bite");
		title.getStyle().set("font-size", "24px").set("color", "#333");

		// Marketing text
		Paragraph description = new Paragraph("Embark on a culinary journey with our restaurant booking platform, "
													  + "where each reservation is a chance to create unforgettable memories. Whether you're planning "
													  + "a romantic dinner, a family celebration, or a casual get-together, our platform offers a seamless "
													  + "experience tailored to your tastes.");

		// Reservation Button with inline CSS
		Button reservationButton = new Button("Reserve a Table");
		reservationButton.getStyle().set("background-color", "#4CAF50").set("color", "white");
		reservationButton.addClickListener(e -> {
			// Navigate to the reservation page
			getUI().ifPresent(ui -> ui.navigate("reservation"));
		});

		// Order Button with inline CSS
		Button orderButton = new Button("Order for Home");
		orderButton.getStyle().set("background-color", "#f44336").set("color", "white");
		orderButton.addClickListener(e -> {
			// Navigate to the order page
			getUI().ifPresent(ui -> ui.navigate("order"));
		});

		// buttons
		Div buttonsContainer = new Div(reservationButton, orderButton);
		buttonsContainer.getStyle().set("margin-top", "2vh").set("display", "grid").set("grid-auto-flow", "column").set("grid-column-gap", "2vw");


		// Lorem Ipsum paragraphs
		Paragraph loremIpsum1 = new Paragraph("Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
													  + "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");

		Paragraph loremIpsum2 = new Paragraph("Ut enim ad minim veniam, quis nostrud exercitation ullamco "
													  + "laboris nisi ut aliquip ex ea commodo consequat.");

		Paragraph loremIpsum3 = new Paragraph("Duis aute irure dolor in reprehenderit in voluptate velit esse "
													  + "cillum dolore eu fugiat nulla pariatur.");

		Paragraph loremIpsum4 = new Paragraph("Excepteur sint occaecat cupidatat non proident, sunt in culpa qui "
													  + "officia deserunt mollit anim id est laborum.");

		// Main layout
		add(title, description, buttonsContainer, loremIpsum1, loremIpsum2, loremIpsum3, loremIpsum4);
		setAlignItems(Alignment.CENTER);
	}

}
