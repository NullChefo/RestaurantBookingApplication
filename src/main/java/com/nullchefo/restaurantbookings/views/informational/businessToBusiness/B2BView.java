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
package com.nullchefo.restaurantbookings.views.informational.businessToBusiness;

import com.nullchefo.restaurantbookings.views.MainLayout;
import com.nullchefo.restaurantbookings.views.informational.contacts.ContactView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("B2B")
@Route(value = "b2b", layout = MainLayout.class)
@AnonymousAllowed
public class B2BView extends VerticalLayout {

	public B2BView() {
		addClassName("b2b-page");

		// Header
		H1 title = new H1("Welcome to Our B2B Platform");
		title.addClassName("b2b-title");

		// Description
		Paragraph description = new Paragraph(
				"Connect with other businesses, streamline processes, and boost collaboration with our B2B platform.");

		// Features
		Paragraph featuresTitle = new Paragraph("Key Features:");
		featuresTitle.addClassName("b2b-section-title");

		Paragraph feature1 = new Paragraph(
				"1. Seamless Collaboration: Collaborate effortlessly with other businesses to achieve mutual success.");
		Paragraph feature2 = new Paragraph(
				"2. Streamlined Processes: Simplify and optimize your business processes for increased efficiency.");
		Paragraph feature3 = new Paragraph(
				"3. Secure Transactions: Ensure the security of your transactions with our robust security measures.");

		// Call-to-Action Button with Navigation
		RouterLink contactLink = new RouterLink("Contact Us", ContactView.class);
		contactLink.setQueryParameters(QueryParameters.fromString("b2b"));

		// Add components to the layout
		add(title, description, featuresTitle, feature1, feature2, feature3, contactLink);
	}
}
