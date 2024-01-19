package com.nullchefo.restaurantbookings.views;

import com.nullchefo.restaurantbookings.configuration.security.AuthenticatedUser;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;

public class PublicLayout extends AppLayout {

	private H2 viewTitle;

	private AuthenticatedUser authenticatedUser;
	private AccessAnnotationChecker accessChecker;

	public PublicLayout(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker) {
		this.authenticatedUser = authenticatedUser;
		this.accessChecker = accessChecker;

		setPrimarySection(Section.DRAWER);


	}






}
