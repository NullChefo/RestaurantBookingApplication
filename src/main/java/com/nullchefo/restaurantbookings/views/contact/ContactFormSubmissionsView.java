package com.nullchefo.restaurantbookings.views.contact;

import com.nullchefo.restaurantbookings.entity.ContactFormSubmission;
import com.nullchefo.restaurantbookings.service.ContactFormSubmissionService;
import com.nullchefo.restaurantbookings.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@PageTitle("Submissions Dashboard")
@Route(value = "contact-form-submissions", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class ContactFormSubmissionsView extends VerticalLayout {

	private final ContactFormSubmissionService contactFormSubmissionService;

	public ContactFormSubmissionsView(ContactFormSubmissionService contactFormSubmissionService) {
		this.contactFormSubmissionService = contactFormSubmissionService;
		setSizeFull();
		setSpacing(false);
		add(createContactFormSubmissionGrid());
	}

	private ContactFormSubmissionGrid createContactFormSubmissionGrid(){
		ContactFormSubmissionGrid grid = new ContactFormSubmissionGrid();
		grid.setItems(contactFormSubmissionService.findAll());
		return grid;
	}


}
