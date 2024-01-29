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
package com.nullchefo.restaurantbookings.views.auth.registration;

import com.nullchefo.restaurantbookings.views.informational.privacyPolicy.PrivacyPolicyView;
import com.nullchefo.restaurantbookings.views.informational.termsOfService.TermsOfServiceView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.map.configuration.View;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import java.util.stream.Stream;


import lombok.AllArgsConstructor;
import lombok.Getter;

public class RegistrationForm extends FormLayout {
	private H3 title;
	private TextField firstName;
	private TextField lastName;
	private EmailField email;
	private TextField username;
	private PasswordField password;
	private PasswordField passwordConfirm;
	private Checkbox subscribeForMarketing;
	private Checkbox subscribeForNotifications;
	private Checkbox subscribeForAnnouncements;
	private Checkbox isOrganization;

	@Getter
	private Span errorMessageField;
	@Getter
	private Button submitButton;

	public RegistrationForm() {

		PrivacyPolicyView privacyPolicyView = new PrivacyPolicyView();
		TermsOfServiceView termsOfServiceView = new TermsOfServiceView();

		title = new H3("Signup form");
		firstName = new TextField("First name");
		lastName = new TextField("Last name");
		email = new EmailField("Email");

		username = new TextField("Username");

		subscribeForMarketing = new Checkbox("Allow marketing emails");
		subscribeForMarketing.getStyle().set("margin-top", "10px");

		isOrganization = new Checkbox("Register as organisation");
		subscribeForMarketing.getStyle().set("margin-top", "10px");

		subscribeForNotifications = new Checkbox("Allow notifications");
		subscribeForNotifications.getStyle().set("margin-top", "10px");

		subscribeForAnnouncements = new Checkbox("Allow announcements");
		subscribeForAnnouncements.getStyle().set("margin-top", "10px");

		password = new PasswordField("Password");
		passwordConfirm = new PasswordField("Confirm password");

		setRequiredIndicatorVisible(firstName, lastName, email, password, username,
									passwordConfirm);

		errorMessageField = new Span();

		submitButton = new Button("Sign up");
		submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		HorizontalLayout privacyPolicyContainer = new HorizontalLayout();

		// Adding privacy policy link
		Paragraph privacyPolicyMessage = new Paragraph("By registering you agree to our ");
		Button privacyPolicyLink = createButtonThatLooksLikeLink("Privacy policy");
		privacyPolicyLink.getElement().addEventListener("click", e -> showDialog(privacyPolicyView));

		// Adding terms of service link
		Paragraph termsOfServiceMessage = new Paragraph(" and our ");
		Button termsOfServiceLink = createButtonThatLooksLikeLink("Terms of Service");
		termsOfServiceLink.getElement().addEventListener("click", e -> showDialog(termsOfServiceView));

		// Set white-space property to nowrap for Paragraphs
		privacyPolicyMessage.getElement().getStyle().set("white-space", "nowrap");
		termsOfServiceMessage.getElement().getStyle().set("white-space", "nowrap");

		// Set spacing, center alignment, and add components to the layout
		privacyPolicyContainer.setSpacing(true);
		privacyPolicyContainer.setAlignItems(FlexComponent.Alignment.CENTER);
		privacyPolicyContainer.add(privacyPolicyMessage, privacyPolicyLink, termsOfServiceMessage, termsOfServiceLink);

		privacyPolicyContainer.add(privacyPolicyMessage, privacyPolicyLink, termsOfServiceMessage, termsOfServiceLink);


		HorizontalLayout alreadyHaveAccount = new HorizontalLayout();
		Paragraph alreadyHaveAccountPar = new Paragraph("Already have account. ");
		Anchor alreadyHaveAccountLink = new Anchor("/login", "Sign in");
		alreadyHaveAccountLink.getStyle().set("margin", "0px").set("padding", "0px").set("color", "blue");
		alreadyHaveAccount.add(alreadyHaveAccountPar, alreadyHaveAccountLink);
		alreadyHaveAccount.setAlignItems(FlexComponent.Alignment.CENTER);

		VerticalLayout verticalLayout = new VerticalLayout();

		verticalLayout.add(privacyPolicyContainer, alreadyHaveAccount);


		add(title, firstName, lastName, email , username, password,
			passwordConfirm, subscribeForMarketing, isOrganization, subscribeForNotifications, subscribeForAnnouncements, errorMessageField,
			submitButton,verticalLayout);

		// Max width of the Form
		setMaxWidth("70vh");
		// Allow the form layout to be responsive.
		// On device widths 0-490px we have one column.
		// Otherwise, we have two columns.
		setResponsiveSteps(
				new ResponsiveStep("0", 1, ResponsiveStep.LabelsPosition.TOP),
				new ResponsiveStep("50vh", 2, ResponsiveStep.LabelsPosition.TOP));

		// These components always take full width
		setColspan(title, 2);
		setColspan(email, 2);
		setColspan(username, 2);
		setColspan(errorMessageField, 2);
		setColspan(submitButton, 2);
	}

	public PasswordField getPasswordField() { return password; }

	public PasswordField getPasswordConfirmField() { return passwordConfirm; }

	private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
		Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
	}


	private void showDialog(Component view) {
		Dialog dialog = new Dialog();
		dialog.add(view);
		dialog.setMaxWidth("70vw");
		dialog.setMaxHeight("90vh");
		dialog.open();
	}

	private Button createButtonThatLooksLikeLink(String text) {
		Button button = new Button(text);
		button.getStyle().set("background-color", "transparent");
		button.getStyle().set("border", "none");
		button.getStyle().set("color", "blue");
		button.getStyle().set("text-decoration", "underline");
		button.getStyle().set("cursor", "pointer");
		button.getStyle().set("padding", "0px");
		button.getStyle().set("margin", "0px");
		return button;
	}
}
