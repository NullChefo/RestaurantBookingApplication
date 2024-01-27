package com.nullchefo.restaurantbookings.views.emailValidation;

import com.nullchefo.restaurantbookings.entity.User;
import com.nullchefo.restaurantbookings.exceptionControl.exceptions.EntityNotValidException;
import com.nullchefo.restaurantbookings.service.UserService;
import com.nullchefo.restaurantbookings.views.informational.error.ErrorView;
import com.nullchefo.restaurantbookings.views.user.login.LoginView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

@Route("validate-email")
public class EmailValidationView extends VerticalLayout implements BeforeEnterObserver {

	private final UserService userService;

	@Autowired
	public EmailValidationView(UserService userService) {
		this.userService = userService;
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		// Retrieve token from the query parameters
		Map<String, List<String>> parameters = event.getLocation().getQueryParameters().getParameters();
		String token = parameters.getOrDefault("token", List.of("")).get(0);


		// Check if the token is valid (You should replace this with your actual validation logic)
		boolean isValidToken = isValidToken(token);

		// Show notification based on token validity
		if (isValidToken) {
			Notification.show("Email validation successful!");
			// Redirect to the login page
			UI.getCurrent().navigate(LoginView.class);
		} else {
			Notification.show("Invalid token. Please try again or contact support.");
			// Redirect to an error page or take appropriate action
			event.forwardTo(ErrorView.class);
		}
	}

	// Replace this method with your actual token validation logic
	private boolean isValidToken(String token) {
		// Example: Check if the token exists in the database
		// You should implement your database logic here

		boolean isValidToken = false;

		if (token == null) {
			return false;
		}


		try {
			userService.validateVerificationToken(token);
			isValidToken = true;
		}
		catch (Exception e) {

		}

		return isValidToken;
	}
}
