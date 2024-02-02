package com.nullchefo.restaurantbookings.views.user.editUser;


import com.nullchefo.restaurantbookings.configuration.security.AuthenticatedUser;
import com.nullchefo.restaurantbookings.entity.User;
import com.nullchefo.restaurantbookings.service.UserService;
import com.nullchefo.restaurantbookings.views.MainLayout;
import com.nullchefo.restaurantbookings.views.auth.login.LoginView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@PageTitle("Edit users")
@Route(value = "user/edit", layout = MainLayout.class)
@PermitAll
public class EditUserView extends VerticalLayout {


private EditUserView(){
	add(new H1("To be implemented"));
}


}
