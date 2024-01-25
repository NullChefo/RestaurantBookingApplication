package com.nullchefo.restaurantbookings.views.informational.legalInformation;

import com.nullchefo.restaurantbookings.views.MainLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.component.html.*;
@PageTitle("Legal information")
@Route(value = "legal-information", layout = MainLayout.class)
@AnonymousAllowed
public class LegalInformationView extends VerticalLayout {

	public LegalInformationView() {
		setMargin(true);
		setPadding(true);
		setSpacing(true);

		H1 header = new H1("Legal Information");
		header.getStyle().set("margin-bottom", "2.5vh");

		add(header);



		// Privacy Policy
		Div privacyPolicySection = createSection("Privacy Policy");
		privacyPolicySection.add(new Paragraph(
				"Our Privacy Policy outlines how we collect, use, and protect your personal data. "
						+ "It also explains your rights and choices regarding your information."));
		privacyPolicySection.add(new Paragraph(createLink(
				"For more details, please review our full Privacy Policy.",
				"/privacy-policy")));

		// Cookie Policy
		Div cookiePolicySection = createSection("Cookie Policy");
		cookiePolicySection.add(new Paragraph(
				"Our Cookie Policy provides information about the types of cookies we use, their purposes, "
						+ "and how you can manage or disable them in your browser settings."));
		cookiePolicySection.add(new Paragraph(createLink(
				"To learn more, refer to our dedicated Cookie Policy page.",
				"/cookie-policy")));

		// Terms of Service
		Div termsOfServiceSection = createSection("Terms of Service");
		termsOfServiceSection.add(new Paragraph("Our Terms of Service govern your use of our website and services. "
														+ "By accessing or using our site, you agree to comply with these terms."));
		termsOfServiceSection.add(new Paragraph(createLink(
				"Please read our complete Terms of Service for detailed information.",
				"/terms-of-service")));

		// Add sections to the main layout
		add(privacyPolicySection, cookiePolicySection, termsOfServiceSection);
	}

	private Div createSection(String sectionTitle) {
		Div section = new Div();
		section.addClassName("legal-section"); // Apply your custom styles if needed
		section.add(new H2(sectionTitle));
		return section;
	}

	private Anchor createLink(String text, String href) {
		Anchor anchor = new Anchor(href, text);
		anchor.setTarget("_blank"); // Open link in a new tab
		return anchor;
	}
}
