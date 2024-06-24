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
package com.nullchefo.restaurantbookings.views.informational.faq;

import com.nullchefo.restaurantbookings.views.MainLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("FAQ")
@Route(value = "faq", layout = MainLayout.class)
@AnonymousAllowed
public class FaqView extends VerticalLayout {

	public FaqView() {
		setMargin(true);
		setPadding(true);
		setSpacing(true);

		H1 header = new H1("Frequently Asked Questions");
		header.getStyle().set("margin-bottom", "2.5vh");
		add(header);

		// Restaurant Booking
		Div bookingSection = createSection("Restaurant Booking");
		bookingSection.add(createQnA(
				"How can I make a reservation?",
				"You can make a reservation by visiting our website and using the online booking form."));
		bookingSection.add(createQnA(
				"Is there a cancellation fee?",
				"Cancellation policies vary. Please check the specific restaurant's cancellation policy when making a reservation."));
		bookingSection.add(createQnA(
				"Can I modify my reservation?",
				"Yes, you can modify your reservation by contacting our customer support or using the online modification form."));
		// Food Delivery
		Div deliverySection = createSection("Food Delivery");
		deliverySection.add(createQnA(
				"How does food delivery work?",
				"You can place an order through our website or mobile app, and the food will be delivered to your specified location."));
		deliverySection.add(createQnA(
				"What is the delivery time?",
				"Delivery times may vary based on your location and the restaurant. You will receive an estimated delivery time when placing your order."));
		deliverySection.add(createQnA(
				"Is there a minimum order amount?",
				"Minimum order amounts may apply, depending on the restaurant. Please check the restaurant's details when placing an order."));

		// Add sections to the main layout
		add(bookingSection, deliverySection);
	}

	private Div createSection(String sectionTitle) {
		Div section = new Div();
		section.addClassName("faq-section"); // Apply your custom styles if needed
		section.add(new H2(sectionTitle));
		return section;
	}

	private Div createQnA(String question, String answer) {
		Div qnaDiv = new Div();
		qnaDiv.addClassName("faq-qna"); // Apply your custom styles if needed
		qnaDiv.add(new H2(question));
		qnaDiv.add(new Paragraph(answer));
		return qnaDiv;
	}
}

