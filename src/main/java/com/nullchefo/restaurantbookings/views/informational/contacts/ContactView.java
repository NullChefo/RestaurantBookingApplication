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
package com.nullchefo.restaurantbookings.views.informational.contacts;

import static com.nullchefo.restaurantbookings.utils.StaticContent.ORGANISATION_ADDRESS;
import static com.nullchefo.restaurantbookings.utils.StaticContent.PROJECT_NAME;
import static com.nullchefo.restaurantbookings.utils.StaticContent.SUPPORT_EMAIL;

import com.nullchefo.restaurantbookings.entity.ContactFormSubmission;
import com.nullchefo.restaurantbookings.service.ContactFormSubmissionService;
import com.nullchefo.restaurantbookings.service.MailProduceService;
import com.nullchefo.restaurantbookings.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Contacts")
@Route(value = "contact", layout = MainLayout.class)
@AnonymousAllowed
public class ContactView extends VerticalLayout {

	private final Binder<ContactFormSubmission> binder = new Binder<>(ContactFormSubmission.class);
	private final ContactFormSubmissionService contactFormSubmissionService;

	private final MailProduceService mailProduceService;

	public ContactView(
			ContactFormSubmissionService contactFormSubmissionService,
			MailProduceService mailProduceService) {
		this.contactFormSubmissionService = contactFormSubmissionService;
		this.mailProduceService = mailProduceService;
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

		TextArea messageField = new TextArea("Message");
		messageField.setWidth("100%");

		TextField reasonField = new TextField("Reason for Contact");
		reasonField.setWidth("100%");

		contactFormLayout.add(name, email, messageField, reasonField);

		// Bind form fields to ContactFormSubmission object
		binder.bind(name, ContactFormSubmission::getName, ContactFormSubmission::setName);
		binder.bind(email, ContactFormSubmission::getEmail, ContactFormSubmission::setEmail);
		binder.bind(messageField, ContactFormSubmission::getMessage, ContactFormSubmission::setMessage);
		binder.bind(
				reasonField,
				ContactFormSubmission::getReasonForSubmission,
				ContactFormSubmission::setReasonForSubmission);

		// Add validators if needed
		binder
				.forField(name)
				.asRequired("Name cannot be empty")
				.bind(ContactFormSubmission::getName, ContactFormSubmission::setName);
		binder
				.forField(email)
				.asRequired("Email cannot be empty")
				.bind(ContactFormSubmission::getEmail, ContactFormSubmission::setEmail);
		binder
				.forField(messageField)
				.asRequired("Message cannot be empty")
				.bind(ContactFormSubmission::getMessage, ContactFormSubmission::setMessage);
		binder
				.forField(reasonField)
				.asRequired("Reason cannot be empty")
				.bind(ContactFormSubmission::getReasonForSubmission, ContactFormSubmission::setReasonForSubmission);

		// Submit Button
		Button submitButton = new Button("Submit");

		submitButton.addClickListener(e -> {
			ContactFormSubmission contactFormSubmissionBean = new ContactFormSubmission();
			// Run validators and write the values to the bean
			try {
				binder.writeBean(contactFormSubmissionBean);
			} catch (ValidationException ex) {
				throw new RuntimeException(ex);
			}
			if (binder.validate().isOk()) {
				handleFormSubmission(contactFormSubmissionBean);
			} else {
				Notification.show("Please fill in all required fields");
			}
		});

		// Add components to the layout
		add(title, nameInfo, addressInfo, supportEmail, contactFormLayout, submitButton);
	}

	private void handleFormSubmission(ContactFormSubmission submission) {

		this.contactFormSubmissionService.create(submission);
		this.mailProduceService.sendContactFormEmail(submission);
	}

}
