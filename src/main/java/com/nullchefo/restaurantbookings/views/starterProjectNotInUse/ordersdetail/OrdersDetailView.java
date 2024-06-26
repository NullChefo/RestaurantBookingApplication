package com.nullchefo.restaurantbookings.views.starterProjectNotInUse.ordersdetail;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import com.nullchefo.restaurantbookings.entity.User;
import com.nullchefo.restaurantbookings.service.UserService;
import com.nullchefo.restaurantbookings.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;

@PageTitle("Orders Detail")
@Route(value = "orders-detail/:userID?/:action?(edit)", layout = MainLayout.class)
@AnonymousAllowed
@Uses(Icon.class)
public class OrdersDetailView extends Div implements BeforeEnterObserver {

	private final String USER_ID = "userID";
	private final String USER_EDIT_ROUTE_TEMPLATE = "orders-detail/%s/edit";

	private final Grid<User> grid = new Grid<>(User.class, false);
	private final Button cancel = new Button("Cancel");
	private final Button save = new Button("Save");
	private final BeanValidationBinder<User> binder;
	private final UserService userService;
	private TextField firstName;
	private TextField lastName;
	private TextField email;
	private TextField phone;
	private DatePicker dateOfBirth;
	private TextField occupation;
	private TextField role;
	private Checkbox important;
	private User user;

	public OrdersDetailView(UserService userService) {
		this.userService = userService;
		addClassNames("orders-detail-view");

		// Create UI
		SplitLayout splitLayout = new SplitLayout();

		createGridLayout(splitLayout);
		createEditorLayout(splitLayout);

		add(splitLayout);

		// Configure Grid
		grid.addColumn("firstName").setAutoWidth(true);
		grid.addColumn("lastName").setAutoWidth(true);
		grid.addColumn("email").setAutoWidth(true);
		grid.addColumn("phone").setAutoWidth(true);
		grid.addColumn("dateOfBirth").setAutoWidth(true);
		grid.addColumn("occupation").setAutoWidth(true);
		grid.addColumn("role").setAutoWidth(true);

		// TODO change
		LitRenderer<User> importantRenderer = LitRenderer.<User>of(
																 "<vaadin-icon icon='vaadin:${item.icon}' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: ${item.color};'></vaadin-icon>")
														 .withProperty(
																 "icon",
																 important -> important.isImportant() ?
																		 "check" :
																		 "minus").withProperty(
						"color",
						important -> important.isImportant()
								? "var(--lumo-primary-text-color)"
								: "var(--lumo-disabled-text-color)");

		grid.addColumn(importantRenderer).setHeader("Important").setAutoWidth(true);

		grid.setItems(query -> userService.list(
												  PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
										  .stream());
		grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

		// when a row is selected or deselected, populate form
		grid.asSingleSelect().addValueChangeListener(event -> {
			if (event.getValue() != null) {
				UI.getCurrent().navigate(String.format(USER_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
			} else {
				clearForm();
				UI.getCurrent().navigate(OrdersDetailView.class);
			}
		});

		// Configure Form
		binder = new BeanValidationBinder<>(User.class);

		// Bind fields. This is where you'd define e.g. validation rules

		binder.bindInstanceFields(this);

		cancel.addClickListener(e -> {
			clearForm();
			refreshGrid();
		});

		save.addClickListener(e -> {
			try {
				if (this.user == null) {
					this.user = new User();
				}
				binder.writeBean(this.user);
				userService.update(this.user);
				clearForm();
				refreshGrid();
				Notification.show("Data updated");
				UI.getCurrent().navigate(OrdersDetailView.class);
			} catch (ObjectOptimisticLockingFailureException exception) {
				Notification n = Notification.show(
						"Error updating the data. Somebody else has updated the record while you were making changes.");
				n.setPosition(Position.MIDDLE);
				n.addThemeVariants(NotificationVariant.LUMO_ERROR);
			} catch (ValidationException validationException) {
				Notification.show("Failed to update the data. Check again that all values are valid");
			}
		});
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {

		Optional<UUID> userId = event.getRouteParameters().get(USER_ID).map(UUID::fromString);

		if (userId.isPresent()) {
			Optional<User> userFromBackend = userService.findByIdOptional(userId.get());
			if (userFromBackend.isPresent()) {
				populateForm(userFromBackend.get());
			} else {
				Notification.show(
						String.format("The requested user was not found, ID = %s", userId.get()), 3000,
						Notification.Position.BOTTOM_START);
				// when a row is selected but the data is no longer available,
				// refresh grid
				refreshGrid();
				event.forwardTo(OrdersDetailView.class);
			}
		}
	}

	private void createEditorLayout(SplitLayout splitLayout) {
		Div editorLayoutDiv = new Div();
		editorLayoutDiv.setClassName("editor-layout");

		Div editorDiv = new Div();
		editorDiv.setClassName("editor");
		editorLayoutDiv.add(editorDiv);

		FormLayout formLayout = new FormLayout();
		firstName = new TextField("First Name");
		lastName = new TextField("Last Name");
		email = new TextField("Email");
		phone = new TextField("Phone");
		dateOfBirth = new DatePicker("Date Of Birth");
		occupation = new TextField("Occupation");
		role = new TextField("Role");
		important = new Checkbox("Important");
		formLayout.add(firstName, lastName, email, phone, dateOfBirth, occupation, role, important);

		editorDiv.add(formLayout);
		createButtonLayout(editorLayoutDiv);

		splitLayout.addToSecondary(editorLayoutDiv);
	}

	private void createButtonLayout(Div editorLayoutDiv) {
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setClassName("button-layout");
		cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		buttonLayout.add(save, cancel);
		editorLayoutDiv.add(buttonLayout);
	}

	private void createGridLayout(SplitLayout splitLayout) {
		Div wrapper = new Div();
		wrapper.setClassName("grid-wrapper");
		splitLayout.addToPrimary(wrapper);
		wrapper.add(grid);
	}

	private void refreshGrid() {
		grid.select(null);
		grid.getDataProvider().refreshAll();
	}

	private void clearForm() {
		populateForm(null);
	}

	private void populateForm(User value) {
		this.user = value;
		binder.readBean(this.user);

	}
}
