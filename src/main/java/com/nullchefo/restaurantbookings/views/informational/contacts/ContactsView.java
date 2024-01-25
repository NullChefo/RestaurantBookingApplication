package com.nullchefo.restaurantbookings.views.informational.contacts;

import com.nullchefo.restaurantbookings.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Contacts")
@Route(value = "contacts", layout = MainLayout.class)
@AnonymousAllowed
public class ContactsView extends VerticalLayout {

	public ContactsView() {
		setMargin(true);
		setPadding(true);

		H1 header = new H1("Contacts");
		header.getStyle().set("margin-bottom", "2.5vh");
		add(header);

		// Sample contacts data
		Contact johnDoe = new Contact("John Doe", "john.doe@example.com", "+1 123 456 789");
		Contact janeSmith = new Contact("Jane Smith", "jane.smith@example.com", "+1 987 654 321");

		// Create card for each contact
		Div johnCard = createContactCard(johnDoe);
		Div janeCard = createContactCard(janeSmith);

		// Add cards to the layout
		add(johnCard, janeCard);
	}

	private Div createContactCard(Contact contact) {
		Div card = new Div();
		card.setWidth("300px");
		card.getStyle().set("border", "1px solid #ddd"); // Add border for card-like appearance
		card.setClassName("contact-card"); // Add your custom styles if needed

		// Header with contact name
		Div header = new Div();
		header.setText(contact.getName());
		header.getStyle().set("font-weight", "bold");

		// Content with contact details
		Div detailsDiv = new Div();
		detailsDiv.add(new TextField("Email", contact.getEmail()));
		detailsDiv.add(new TextField("Phone", contact.getPhone()));

		// Footer with action buttons
		HorizontalLayout footerLayout = new HorizontalLayout();
		footerLayout.setSpacing(true);

		Button editButton = new Button("Edit");
		Button deleteButton = new Button("Delete");

		footerLayout.add(editButton, deleteButton);

		card.add(header, detailsDiv, footerLayout);

		return card;
	}

	private static class Contact {
		private final String name;
		private final String email;
		private final String phone;

		public Contact(String name, String email, String phone) {
			this.name = name;
			this.email = email;
			this.phone = phone;
		}

		public String getName() {
			return name;
		}

		public String getEmail() {
			return email;
		}

		public String getPhone() {
			return phone;
		}
	}
}
