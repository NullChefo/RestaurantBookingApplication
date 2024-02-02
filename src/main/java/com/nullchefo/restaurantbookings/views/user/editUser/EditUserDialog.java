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
package com.nullchefo.restaurantbookings.views.user.editUser;


import com.nullchefo.restaurantbookings.entity.Location;
import com.nullchefo.restaurantbookings.entity.User;
import com.nullchefo.restaurantbookings.service.UserService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

public class EditUserDialog extends Dialog {

	private final UserService userService;

	private final User user;

	private Button saveButton;
	private Binder<User> binder;

	public EditUserDialog(UserService userService, User user) {
		this.userService = userService;
		this.user = user;
		initContent();
	}

	private void initContent() {
		setHeaderTitle(user.getId() != null ? "Edit" : "Add");
		configureContent();
		configureFooter();
/*        setWidth("300px");
        setHeight("500px");*/
		setCloseOnOutsideClick(false);
		setCloseOnEsc(true);
	}

	private void configureContent() {

//		@ManyToOne
//		private Location currentLocation;
//
//		@Column(length = 999)
//		private String pictureURL;
//
//		private LocalDate dateOfBirth;
//		private String occupation;
//
//		@Column(nullable = false, length = 256, unique = true)
//		private String username;
//
//		@Column(nullable = false, length = 50)
//		private String firstName;
//		@Column(length = 100)
//		private String lastName;






		TextField firstName = new TextField("First name");
		firstName.setRequired(true);

		TextField lastName = new TextField("Last name");
		lastName.setRequired(true);


		TextField username = new TextField("Username");
		username.setRequired(true);

		TextField pictureURL = new TextField("Picture url");
		pictureURL.setRequired(false);



		binder = new Binder<>(User.class);
		binder.forField(firstName).asRequired("First name is required")
			  .bind(User::getFirstName, User::setFirstName)
			  .setAsRequiredEnabled(true);

		binder.forField(lastName).asRequired("Last name is required")
			  .bind(User::getLastName, User::setLastName)
			  .setAsRequiredEnabled(true);

		binder.forField(username).asRequired("Username is required")
			  .bind(User::getUsername, User::setUsername)
			  .setAsRequiredEnabled(true);

		binder.forField(pictureURL).bind(User::getPictureURL, User::setPictureURL);



		binder.readBean(user);
		FormLayout formLayout = new FormLayout(firstName, lastName, username, pictureURL);
		add(formLayout);
	}

	private void configureFooter() {
		saveButton = new Button("Create");
		saveButton.addClickListener(save());
		saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		Button cancelButton = new Button("Cancel", e -> close());
		getFooter().add(cancelButton);
		getFooter().add(saveButton);
	}

	private ComponentEventListener<ClickEvent<Button>> save() {
		return ll -> {
			try {
				binder.writeBean(user);
				userService.create(user);
				close();
			} catch (ValidationException e) {
				throw new RuntimeException(e);
			}
		};
	}

	public void addSaveClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
		saveButton.addClickListener(listener);
	}




}
