package com.nullchefo.restaurantbookings.views.reservation.edit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.nullchefo.restaurantbookings.entity.Dish;
import com.nullchefo.restaurantbookings.entity.Reservation;
import com.nullchefo.restaurantbookings.service.OntologyManagerService;
import com.nullchefo.restaurantbookings.service.ReservationService;
import com.nullchefo.restaurantbookings.views.ontology.DishGrid;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.selection.MultiSelect;

public class ReservationDialog extends Dialog {

	private final Reservation reservation;

	private final ReservationService reservationService;

	private Button saveButton;
	private Binder<Reservation> binder;
	private IngredientGrid ingredientGrid = new IngredientGrid();

	private final TextField note = new TextField("Note");

	private final OntologyManagerService  ontologyManagerService;

	public ReservationDialog(Reservation reservation, ReservationService reservationService,
							 final OntologyManagerService ontologyManagerService) {
		this.reservation = reservation;
		this.reservationService = reservationService;
		this.ontologyManagerService = ontologyManagerService;
		initContent();
	}

	private void initContent() {
		setHeaderTitle("Edit");
		add(new H1("Select unwanted ingredients"));
		add(createGrid());
		configureContent();
		configureFooter();
		setCloseOnOutsideClick(false);
		setCloseOnEsc(true);

		setMinHeight("80vh");
		setMinWidth("60vw");

		reloadGrid();
	}

	private void configureContent() {

		binder = new Binder<>(Reservation.class);

		this.note.setEnabled(false);
		binder.forField(note).bind(Reservation::getNotes, Reservation::setNotes);

		binder.readBean(reservation);
		FormLayout formLayout = new FormLayout(note);
		add(formLayout);
	}

	private void configureFooter() {
		saveButton = new Button("Save");
		saveButton.addClickListener(save());
		saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		Button cancelButton = new Button("Cancel", e -> close());
		getFooter().add(cancelButton);
		getFooter().add(saveButton);
	}

	private ComponentEventListener<ClickEvent<Button>> save() {
		return ll -> {
			try {
				binder.writeBean(reservation);
				reservationService.create(reservation);
				close();
			} catch (ValidationException e) {
				throw new RuntimeException(e);
			}
		};
	}

	public void addSaveClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
		saveButton.addClickListener(listener);
	}

	private void reloadGrid() {
		// list of ingredientClass
		List<IngredientClass> allIngredientsClass = ontologyManagerService.getAllIngredients();
		ingredientGrid.setItems(allIngredientsClass);


		// in the string there ingredient names that must be ignored; the string is with that format IgnoreIngredients=name1,name2....
		String reservationNote = reservation.getNotes();
		List<String> ignoreIngredientNames = new ArrayList<>();

		// Check if reservationNote contains IgnoreIngredients=
		if (reservationNote != null && reservationNote.startsWith("IgnoreIngredients=")) {
			// Get the substring after "IgnoreIngredients="
			String ingredientsString = reservationNote.substring("IgnoreIngredients=".length());

			// Split the string by commas to get individual ingredient names
			String[] ingredientNames = ingredientsString.split(",");

			// Add each ingredient name to the list
			ignoreIngredientNames.addAll(List.of(ingredientNames));
		}

		List<IngredientClass> ignoredIngredients = new ArrayList<>();

		for (IngredientClass ingredientClass : allIngredientsClass) {
			if (ignoreIngredientNames.contains(ingredientClass.getName())) {
				ignoredIngredients.add(ingredientClass);
			}
		}

		if (!ignoredIngredients.isEmpty()) {
			ingredientGrid.asMultiSelect()
						  .select(new HashSet<>(ignoredIngredients));
		}


	}


	private IngredientGrid createGrid() {
		this.ingredientGrid = new IngredientGrid();
		this.ingredientGrid.setMaxHeight("40vh");
//		this.ingredientGrid.setMinHeight("300px");

//		this.ingredientGrid.addItemDoubleClickListener(l -> openDishDialog(l.getItem()));

		// make it to work with multi select

		MultiSelect<Grid<IngredientClass>, IngredientClass> multiSelect = this.ingredientGrid.asMultiSelect();
		multiSelect.addSelectionListener(event -> {
			Set<IngredientClass> selectedItems = event.getAllSelectedItems();

			this.note.setValue(IngredientClass.convertIngridentClassSetToString(selectedItems));
//			boolean enabled = !selectedItems.isEmpty();

//			editDishButton.setEnabled(enabled);
//			removeDishButton.setEnabled(enabled);
//
//			if (selectedItems.size()  > 1) {
//				editDishButton.setEnabled(false);
//			}


		});
		return this.ingredientGrid;
	}

}
