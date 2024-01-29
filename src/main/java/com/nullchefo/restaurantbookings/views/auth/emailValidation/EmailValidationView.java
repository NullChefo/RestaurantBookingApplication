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
package com.nullchefo.restaurantbookings.views.auth.emailValidation;

import com.nullchefo.restaurantbookings.service.UserService;
import com.nullchefo.restaurantbookings.views.auth.login.LoginView;
import com.nullchefo.restaurantbookings.views.informational.error.ErrorView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("auth/verifyRegistration")
@AnonymousAllowed
public class EmailValidationView extends VerticalLayout implements BeforeEnterObserver {

	private final UserService userService;
	private boolean isValidToken = false;

	private String token = "";

	public EmailValidationView(UserService userService) {
		this.userService = userService;

		if (!isValidToken) {
			Div activationMessageDiv = new Div();
			activationMessageDiv.addClassName("flex-center text-center text-2xl");
			activationMessageDiv.setText("Your activation mail is expired!");

			// Second div
			Div buttonDiv = new Div();
			buttonDiv.addClassName("pt-8 flex-center text-center pb-16");

			Button requestButton = new Button("Request new one");
			requestButton.addClassName("bg-amber-600 rounded-lg text-2xl p-2");
			requestButton.addClickListener(event -> sendNewRegisterToken());

			buttonDiv.add(requestButton);

			// Adding components to the layout
			add(activationMessageDiv, buttonDiv);
		}

	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		String token = event.getRouteParameters().get("id").orElse(null);

		if (token == null) {
			this.isValidToken = false;
			Notification.show("Empty path").setDuration(5000);
			UI.getCurrent().navigate(ErrorView.class);
		}

		// Check if the token is valid (You should replace this with your actual validation logic)
		this.isValidToken = userService.validateVerificationToken(token);

		// Show notification based on token validity
		if (this.isValidToken) {
			Notification.show("Email validation successful!");
			// Redirect to the login page
			UI.getCurrent().navigate(LoginView.class);
		} else {
			Notification.show("Invalid token. Please request new validation token.");
			this.token = token;
		}
	}

	private void sendNewRegisterToken() {
		boolean isGenerated = this.userService.resendVerificationToken(token);
		if (isGenerated) {
			Notification.show("New activation link sent!");
		} else {
			Notification.show("Error sending activation link! Please contact support");
		}

	}

}
