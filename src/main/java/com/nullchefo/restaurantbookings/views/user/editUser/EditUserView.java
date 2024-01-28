package com.nullchefo.restaurantbookings.views.user.editUser;

import com.nullchefo.restaurantbookings.views.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import jakarta.annotation.security.PermitAll;

@PermitAll
@PageTitle("Edit user")
@Route(value = "edit-user", layout = MainLayout.class)
public class EditUserView extends VerticalLayout {


	public EditUserView(){
		add(new H1("Edit user Page"));
	}

}
