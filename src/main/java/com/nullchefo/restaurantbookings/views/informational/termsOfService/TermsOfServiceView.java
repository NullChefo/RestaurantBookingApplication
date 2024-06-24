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
package com.nullchefo.restaurantbookings.views.informational.termsOfService;

import static com.nullchefo.restaurantbookings.utils.StaticContent.ORGANISATION_NAME;
import static com.nullchefo.restaurantbookings.utils.StaticContent.PROJECT_NAME;
import static com.nullchefo.restaurantbookings.utils.StaticContent.SUPPORT_EMAIL;

import com.nullchefo.restaurantbookings.views.MainLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "terms-of-service", layout = MainLayout.class)
@AnonymousAllowed
@PageTitle("Terms of service")
public class TermsOfServiceView extends VerticalLayout {

	private final String projectName = PROJECT_NAME;
	private final String companyName = ORGANISATION_NAME;
	private final String supportEmailAddress = SUPPORT_EMAIL;

	public TermsOfServiceView() {
		setMargin(true);
		setPadding(true);

		// Create an H1 component
		H1 termsOfServiceHeader = new H1(projectName + " Terms of Service");
		termsOfServiceHeader.getStyle().set("margin-bottom", "2.5vh");
		// Set margin and padding for the H1 component
		//		termsOfServiceHeader.getStyle().set("margin", "10vh");
		//		termsOfServiceHeader.getStyle().set("padding", "5px");

		//		termsOfServiceHeader.getStyle().set("margin-top", "2.5vh");

		add(termsOfServiceHeader);

		add(new H2("Introduction"));
		add(new Paragraph("Welcome to " + projectName + ", a social media platform operated by " + companyName + ". " +
								  "These terms of service govern your use of our platform and services. " +
								  "By accessing or using our platform, you agree to be bound by these terms and our privacy policy."));

		add(new H2("Privacy"));
		add(new Paragraph(
				"We take your privacy seriously and comply with the General Data Protection Regulation (GDPR). " +
						"Please refer to our " +
						"<a href=\"/privacy-policy\" style=\"color: var(--lumo-primary-color);\">privacy policy</a> " +
						"for more information on how we collect, use, and protect your personal data."));

		add(new H2("Use of Our Platform"));
		UnorderedList platformList = new UnorderedList(
				new ListItem("Violate any applicable law or regulation"),
				new ListItem("Impersonate any person or entity"),
				new ListItem("Engage in any activity that interferes with or disrupts our platform or services"),
				new ListItem(
						"Upload or transmit any content that is infringing, defamatory, obscene, or otherwise objectionable"),
				new ListItem("Attempt to gain unauthorized access to our platform, user accounts, or computer systems"),
				new ListItem(
						"Use our platform to advertise or promote any products or services without our prior written consent")
		);
		add(platformList);

		add(new H2("Intellectual Property"));
		add(new Paragraph("The content and materials on our platform, including text, graphics, logos, and images, " +
								  "are owned by us or our licensors and are protected by intellectual property laws. " +
								  "You may not use, copy, or reproduce any of our content or materials without our prior written consent."));

		add(new H2("Limitation of Liability"));
		add(new Paragraph(
				"We are not liable for any damages or losses that may arise from your use of our platform or services. "
						+
						"We are not responsible for any content uploaded or transmitted by users of our platform. " +
						"We reserve the right to remove any content that violates these terms or our policies."));

		add(new H2("Changes to These Terms"));
		add(new Paragraph("We may revise these terms of service at any time without notice. " +
								  "Your continued use of our platform after any changes to these terms will be deemed acceptance of those changes."));

		add(new H2("Contact Us"));
		add(new Paragraph("If you have any questions or concerns about these terms of service or our platform, " +
								  "please contact us at " +
								  createSupportEmailLink(supportEmailAddress) + ".")); // Using the extracted method
	}

	private Anchor createSupportEmailLink(String email) {
		Anchor anchor = new Anchor("mailto:" + email, email);
		anchor.getStyle().set("color", "var(--lumo-primary-color)");
		return anchor;
	}
}

