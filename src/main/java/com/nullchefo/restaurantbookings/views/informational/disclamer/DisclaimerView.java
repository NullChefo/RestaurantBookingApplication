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
package com.nullchefo.restaurantbookings.views.informational.disclamer;

import static com.nullchefo.restaurantbookings.utils.StaticContent.ORGANISATION_NAME;
import static com.nullchefo.restaurantbookings.utils.StaticContent.PROJECT_ACTIVITIES;
import static com.nullchefo.restaurantbookings.utils.StaticContent.PROJECT_ANALYTICS_PROVIDERS;
import static com.nullchefo.restaurantbookings.utils.StaticContent.PROJECT_DATA_SOURCES;
import static com.nullchefo.restaurantbookings.utils.StaticContent.PROJECT_LAUNCH_DATE;
import static com.nullchefo.restaurantbookings.utils.StaticContent.PROJECT_NAME;

import com.nullchefo.restaurantbookings.views.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Disclaimer")
@Route(value = "disclaimer", layout = MainLayout.class)
@AnonymousAllowed
public class DisclaimerView extends VerticalLayout {

	private final String projectName = PROJECT_NAME;
	private final String projectActivities = PROJECT_ACTIVITIES;
	private final String companyName = ORGANISATION_NAME;
	private final String dataSources = PROJECT_DATA_SOURCES;
	private final String launchedDate = PROJECT_LAUNCH_DATE.toString();
	private final String analytics = PROJECT_ANALYTICS_PROVIDERS;

	public DisclaimerView() {
		setMargin(true);
		setPadding(true);

		H1 header = new H1("DISCLAIMERS");
		header.getStyle().set("margin-bottom", "2.5vh");
		add(header);

		// Commented out as it seems like it's optional based on the provided HTML
		// add(new H2("THIS IS NOT " + projectActivities + " ADVICE"));

		add(new Paragraph("The data, visualizations, and interpretations presented on " + projectName +
								  " are not " + projectActivities + " advice in any way, shape, or form."));

		add(new H2("DATA SOURCE"));
		add(new Paragraph("The insider trade data presented on " + projectName + " all comes from the " + companyName +
								  " itself. Data is retrieved from the " + dataSources +
								  " API or RSS feed, then parsed and saved to our servers. We do not guarantee the quality or accuracy of any data presented. "
								  +
								  "The data is susceptible to filing errors from SEC and while we have checks in place to ensure that many incorrect forms are filtered out, "
								  +
								  "we cannot get all of them. We will be adding a report feature at a later date."));

		add(new H2("WARRANTY"));
		add(new Paragraph("The data provided by " + projectName
								  + " is without warranty. As stated above we do not guarantee the quality or accuracy "
								  +
								  "and you use the data at your own risk. We do, however, provide the link to the filing for all forms so you can check the data."));

		add(new H2("INTELLECTUAL PROPERTY"));
		add(new Paragraph(projectName + " is owned and operated by " + companyName
								  + ". All code, graphics, logos, and domains are the property of " +
								  companyName
								  + ". The data on this site may not be scrapped, copied, or collected for commercial use."));

		add(new H2("BETA SOFTWARE"));
		add(new Paragraph(
				projectName + " is a brand new product " + launchedDate + " and is still in very active development. " +
						"New features are constantly being added and bugs are constantly being addressed."));

		add(new H2("DATA COLLECTION"));
		add(new Paragraph("The only data collected on " + projectName + " is collected by " + analytics +
								  " information about this can be found on their website."));
	}
}

