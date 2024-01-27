package com.nullchefo.restaurantbookings.views.informational.contacts;

import static com.nullchefo.restaurantbookings.utils.StaticContent.ORGANISATION_ADDRESS;
import static com.nullchefo.restaurantbookings.utils.StaticContent.PROJECT_NAME;
import static com.nullchefo.restaurantbookings.utils.StaticContent.SUPPORT_EMAIL;

import com.nullchefo.restaurantbookings.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

@PageTitle("Contacts")
@Route(value = "contact", layout = MainLayout.class)
@AnonymousAllowed
public class ContactView extends VerticalLayout {

//	public ContactView() {
//		addClassName("contact-page");
//
//		// Header
//		H1 title = new H1("Contact Us");
//		title.addClassName("contact-title");
//
//		// Contact Information
//		FormLayout contactInfoLayout = new FormLayout();
//		contactInfoLayout.addFormItem(new TextField("Name"), "name");
//		contactInfoLayout.addFormItem(new TextField("Location"), "location");
//		contactInfoLayout.addFormItem(new TextField("Address"), "address");
//
//		// Contact Form
//		FormLayout contactFormLayout = new FormLayout();
//		TextArea messageField = new TextArea("Message");
//		ComboBox<String> reasonSelect = new ComboBox<>("Reason for Contact");
//		reasonSelect.setItems("General Inquiry", "Technical Support", "Partnership", "Other");
//
//		contactFormLayout.addFormItem(reasonSelect, "Reason");
//		contactFormLayout.addFormItem(messageField, "Message");
//
//		// Submit Button
//		Button submitButton = new Button("Submit");
//		submitButton.addClickListener(e -> handleFormSubmission(messageField.getValue(), reasonSelect.getValue()));
//
//		// Add components to the layout
//		add(title, contactInfoLayout, contactFormLayout, submitButton);
//	}
//
//	private void handleFormSubmission(String message, String reason) {
//		// Perform actions with the submitted data
//		// For example, send an email, store in a database, etc.
//		System.out.println("Message: " + message);
//		System.out.println("Reason: " + reason);
//	}


	public ContactView() {
		addClassName("contact-page");

		// Header
		H1 title = new H1("Contact Us");
		title.addClassName("contact-title");


		// Contact Information with dynamic values
		Paragraph nameInfo = new Paragraph("Name: ");
		nameInfo.setText(nameInfo.getText() + PROJECT_NAME);

		Paragraph addressInfo = new Paragraph("Address: ");
		addressInfo.setText(addressInfo.getText() + ORGANISATION_ADDRESS);

		Paragraph supportEmail = new Paragraph("Support email: ");
		supportEmail.setText(supportEmail.getText() + SUPPORT_EMAIL);

		// Contact Form

		FormLayout contactFormLayout = new FormLayout();

		TextField name = new TextField("Name");
		name.setWidth("100%");

		TextField email = new TextField("Email");
		email.setWidth("100%");

		TextField messageField = new TextField("Message");
		messageField.setWidth("100%");

		TextField reasonField = new TextField("Reason for Contact");
		reasonField.setWidth("100%");

		// Submit Button
		// Replace the following with your actual logic for handling form submissions
		Button submitButton = new Button("Submit");

		contactFormLayout.add(name);
		contactFormLayout.add(email);
		contactFormLayout.add(messageField);
		contactFormLayout.add(reasonField);





		// TODO send email that is from noreply@nullchefo.com that succesfully recieved the

		submitButton.addClickListener(e -> handleFormSubmission(messageField.getValue(), reasonField.getValue()));

		// Add components to the layout
		add(title,  nameInfo, addressInfo,supportEmail, contactFormLayout, submitButton);
	}

	private void handleFormSubmission(String message, String reason) {
		// Perform actions with the submitted data
		// For example, send an email, store in a database, etc.
		System.out.println("Message: " + message);
		System.out.println("Reason: " + reason);
	}
}
