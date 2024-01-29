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
package com.nullchefo.restaurantbookings.views.informational.developerDisclaimer;

import com.nullchefo.restaurantbookings.views.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Developer disclaimer")
@Route(value = "developer-disclaimer", layout = MainLayout.class)
@AnonymousAllowed
public class DeveloperDisclaimerView extends VerticalLayout {

	public DeveloperDisclaimerView() {
		setPadding(true);
		setMargin(true);
		setSpacing(true);
		setJustifyContentMode(JustifyContentMode.START);
		setAlignItems(Alignment.START);
		setClassName("text-justify"); // Apply your custom styles if needed

		H1 header = new H1("Welcome to our developer build!");
		header.getStyle().set("margin-bottom", "2.5vh");
		add(header);

		add(new Paragraph("Please note that this is a testing environment, " +
								  "and we kindly request that you refrain from using any real or sensitive information during your interactions here."));

		add(new Paragraph(
				"The purpose of this site is to simulate various functionalities and gather feedback for improvement."));

		add(new Paragraph(
				"To ensure your privacy and security, we recommend using fictional or placeholder data when prompted to enter "
						+
						"personal details."));

		add(new Paragraph(
				"This includes names, email addresses, phone numbers, addresses, and any other identifiable information."));

		add(new Paragraph(
				"By using fictitious information, you help us maintain the integrity of our testing environment and protect your own privacy."));

		add(new Paragraph("Remember, this site is not intended for real-world transactions or data storage."));

		add(new Paragraph("We appreciate your cooperation and understanding."));

		add(new Paragraph(
				"If you encounter any issues or have feedback regarding the site's functionality, please don't hesitate to reach "
						+
						"out to our support team."));

		add(new Paragraph("Thank you for being a part of our development process!"));
	}
}
