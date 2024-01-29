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
package com.nullchefo.restaurantbookings.views.informational.privacyPolicy;

import static com.nullchefo.restaurantbookings.utils.StaticContent.ORGANISATION_ADDRESS;
import static com.nullchefo.restaurantbookings.utils.StaticContent.ORGANISATION_NAME;
import static com.nullchefo.restaurantbookings.utils.StaticContent.PROJECT_NAME;
import static com.nullchefo.restaurantbookings.utils.StaticContent.SUPPORT_EMAIL;

import com.nullchefo.restaurantbookings.views.MainLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "privacy-policy", layout= MainLayout.class)
@PageTitle("Privacy Policy")
@AnonymousAllowed
public class PrivacyPolicyView extends VerticalLayout {

	private final String companyAddress = ORGANISATION_ADDRESS; // Replace with the actual company address
	private final String supportAddress = SUPPORT_EMAIL; // Replace with the actual support address
	private final String projectName = PROJECT_NAME;
	private final String companyName = ORGANISATION_NAME;

	public PrivacyPolicyView() {
		setMargin(true);
		setPadding(true);

		H1 header = new H1(projectName +  ": Our Privacy Policy");
		header.getStyle().set("margin-bottom", "2.5vh");

		add(header);

		add(new Paragraph("Our " + companyName + " is part of the Our Company Group which includes Our Company International and " +
								  "Our Company Direct. " +
								  "This privacy policy will explain how our organization uses the personal data we collect from you when you use " +
								  "our website."));

		add(new Paragraph("Topics:"));

		add(createUnorderedList(
				"What data do we collect?",
				"How do we collect your data?",
				"How will we use your data?",
				"How do we store your data?",
				"Marketing",
				"What are your data protection rights?",
				"What are cookies?",
				"How do we use cookies?",
				"What types of cookies do we use?",
				"How to manage your cookies?",
				"Privacy policies of other websites",
				"Changes to our privacy policy",
				"How to contact us",
				"How to contact the appropriate authorities"
							   ));

		add(new H2("What data do we collect?"));
		add(createUnorderedList(
				"Personal identification information (Name, email address, phone number, etc.)",
				"[Add any other data your company collects]"
							   ));

		add(new H2("How do we collect your data?"));
		add(new Paragraph("Our Company collects the following data:"));

		add(createUnorderedList(
				"You directly provide Our Company with most of the data we collect. We collect data and process data when you:",
				"Register online or place an order for any of our products or services.",
				"Voluntarily complete a customer survey or provide feedback on any of our message boards or via email.",
				"Use or view our website via your browser’s cookies.",
				"[Add any other ways your company collects data]"
							   ));

		add(new Paragraph("Our Company may also receive your data indirectly from the following sources:"));

		add(createUnorderedList(
				"[Add any indirect source of data your company has]"
							   ));

		// Continue adding the rest of the content...

		add(new H2("How to contact the appropriate authority"));
		add(new Paragraph("Should you wish to report a complaint or if you feel that Our Company has not addressed your concern in a " +
								  "satisfactory manner, you may contact the Information Commissioner’s Office."));

		add(new Paragraph("Email: " + supportAddress));
		add(new Paragraph("Address: " + companyAddress));
	}

	private UnorderedList createUnorderedList(String... items) {
		UnorderedList list = new UnorderedList();
		for (String item : items) {
			list.add(new ListItem(item));
		}
		return list;
	}
}

