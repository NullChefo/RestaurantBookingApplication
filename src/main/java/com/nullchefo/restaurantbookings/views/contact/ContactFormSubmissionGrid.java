package com.nullchefo.restaurantbookings.views.contact;

import com.nullchefo.restaurantbookings.entity.ContactFormSubmission;
import com.vaadin.flow.component.grid.Grid;

public class ContactFormSubmissionGrid extends Grid<ContactFormSubmission> {
	public ContactFormSubmissionGrid() {
		super(ContactFormSubmission.class, false);
		configureView();
	}

	private void configureView() {

		addColumn(ContactFormSubmission::getName).setHeader("Name");
		addColumn(ContactFormSubmission::getEmail).setHeader("Email");
		addColumn(ContactFormSubmission::getReasonForSubmission).setHeader("Reason");
		addColumn(ContactFormSubmission::getMessage).setHeader("Message").setAutoWidth(true);

		setSizeFull();
	}
}
