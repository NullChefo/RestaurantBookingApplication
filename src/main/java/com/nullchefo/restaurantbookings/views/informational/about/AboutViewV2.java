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
package com.nullchefo.restaurantbookings.views.informational.about;

import com.nullchefo.restaurantbookings.views.MainLayout;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

// Import necessary Vaadin packages
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;

// Define the route for the about page

@PageTitle("About")
@Route(value = "about-v2", layout = MainLayout.class)
@AnonymousAllowed
public class AboutViewV2 extends VerticalLayout {

	public AboutViewV2() {
		addClassName("about-page");

		// Header
		H1 title = new H1("About Our Restaurant Platform");
		title.addClassName("about-title");

		// Content
		Paragraph description = new Paragraph("Welcome to our restaurant booking platform, where you can reserve a table or order delicious food from various restaurants to enjoy at home.");

		// About the Platform
		Div aboutPlatform = new Div();
		aboutPlatform.addClassName("about-section");
		aboutPlatform.add(new H1("Our Platform"));
		aboutPlatform.add(new Paragraph("We strive to provide a seamless and enjoyable experience for our users. With our platform, you can easily browse through different restaurants, make reservations, and order food for home delivery."));

		// Mission Statement
		Div missionStatement = new Div();
		missionStatement.addClassName("about-section");
		missionStatement.add(new H1("Our Mission"));
		missionStatement.add(new Paragraph("Our mission is to connect food enthusiasts with their favorite restaurants. We aim to make the dining experience convenient, enjoyable, and memorable."));

		// Contact Information
		Div contactInfo = new Div();
		contactInfo.addClassName("about-section");
		contactInfo.add(new H1("Contact Us"));
		contactInfo.add(new Paragraph("If you have any questions or feedback, feel free to reach out to us. We value your input and are here to assist you."));

		// Add components to the layout
		add(title, description, aboutPlatform, missionStatement, contactInfo);
	}
}
