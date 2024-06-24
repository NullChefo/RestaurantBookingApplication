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
package com.nullchefo.restaurantbookings.views.auth.thankYouForRegistration;

import com.nullchefo.restaurantbookings.views.auth.login.LoginView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "thanks-for-registration")
@AnonymousAllowed
@PageTitle("Thanks for Registration")
public class ThanksForRegistrationView extends VerticalLayout {

	public ThanksForRegistrationView() {
		setAlignItems(Alignment.CENTER);

		Div container = new Div();
		container.setWidth("400px");
		container.getStyle().set("border", "1px solid #ccc");
		container.getStyle().set("padding", "20px");
		container.getStyle().set("border-radius", "10px");

		H1 title = new H1("Thanks for Your Registration");
		title.getStyle().set("color", "#2196F3");

		//		Icon icon = new Icon(VaadinIcon.ENVELOPE);
		//		icon.getElement().getThemeList().add("primary");

		Paragraph message = new Paragraph("Your account has been created, and a verification email has been sent " +
												  "to your registered email address. Please click on the verification link "
												  +
												  "included in the email to activate your account.");

		Button backButton = new Button("Back to Login", e -> logIn());
		backButton.getElement().getThemeList().add("primary");

		container.add(title, message, backButton);
		add(container);
	}

	private void logIn() {
		// redirect to the login page
		UI.getCurrent().navigate(LoginView.class);
	}
}
