package com.nullchefo.restaurantbookings.views.user.registration;

import com.nullchefo.restaurantbookings.views.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
//@Route("/auth/registration")
@PageTitle("Registration")
@Route(value = "registration")
public class RegistrationView  extends VerticalLayout implements BeforeEnterObserver {
	RegistrationForm registrationForm = new RegistrationForm();
	public RegistrationView() {
		addClassName("login-view");
		setSizeFull();
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);

		// Center the RegistrationForm
		setHorizontalComponentAlignment(Alignment.CENTER, registrationForm);

		add(registrationForm);

		RegistrationFormBinder registrationFormBinder = new RegistrationFormBinder(registrationForm);
		registrationFormBinder.addBindingAndValidation();
	}

	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		// inform the user about an authentication error
		if(beforeEnterEvent.getLocation()
						   .getQueryParameters()
						   .getParameters()
						   .containsKey("error")) {
			// TODO something
		}
	}
}
