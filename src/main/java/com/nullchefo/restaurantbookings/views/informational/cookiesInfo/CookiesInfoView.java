package com.nullchefo.restaurantbookings.views.informational.cookiesInfo;

import com.nullchefo.restaurantbookings.views.MainLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Cookie information")
@Route(value = "cookies-info", layout = MainLayout.class)
@AnonymousAllowed
public class CookiesInfoView extends VerticalLayout {

	public CookiesInfoView() {
		setMargin(true);
		setPadding(true);
		setSpacing(true);


		H1 header = new H1("Cookies Information");
		header.getStyle().set("margin-bottom", "2.5vh");
		add(header);

		// Introduction
		add(new Paragraph("This website uses cookies to enhance the user experience and provide personalized content. "
								  + "By using our website, you consent to the use of cookies in accordance with this Cookies Policy."));

		// Types of Cookies
		Div typesSection = createSection("Types of Cookies");
		typesSection.add(new Paragraph("1. Essential Cookies: These cookies are necessary for the website to function properly. "
											   + "They enable basic functions such as page navigation and access to secure areas of the website."));
		typesSection.add(new Paragraph("2. Analytics Cookies: These cookies help us analyze and improve the performance of our website by collecting and reporting information on how users interact with the site."));
		typesSection.add(new Paragraph("3. Marketing Cookies: These cookies are used to deliver personalized advertisements and content based on your interests and preferences."));

		// How We Use Cookies
		Div usageSection = createSection("How We Use Cookies");
		usageSection.add(new Paragraph("We use cookies for the following purposes:"));
		usageSection.add(new Paragraph("- To personalize your experience by remembering your preferences."));
		usageSection.add(new Paragraph("- To analyze website traffic and optimize the site's performance."));
		usageSection.add(new Paragraph("- To deliver targeted advertisements that may be of interest to you."));

		// Managing Cookies
		Div managingSection = createSection("Managing Cookies");
		managingSection.add(new Paragraph("You can manage or disable cookies in your browser settings. However, please note that disabling certain cookies may affect the functionality of the website."));

		// Additional Information
		Div additionalSection = createSection("Additional Information");
		additionalSection.add(new Paragraph("For more details on how we use cookies and to review our full Cookie Policy, please refer to our dedicated Cookie Policy page."));

		// Add sections to the main layout
		add(typesSection, usageSection, managingSection, additionalSection);
	}

	private Div createSection(String sectionTitle) {
		Div section = new Div();
		section.addClassName("cookies-section"); // Apply your custom styles if needed
		section.add(new H2(sectionTitle));
		return section;
	}
}

