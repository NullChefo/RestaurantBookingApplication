package com.nullchefo.restaurantbookings.views.ontology;



import com.nullchefo.restaurantbookings.entity.Dish;
import com.nullchefo.restaurantbookings.service.OntologyManagerService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

public class DishDialog extends Dialog {

	private final Dish dish;
	private Button saveButton;
	private Binder<Dish> binder;
	private final OntologyManagerService  ontologyManagerService;


	public DishDialog(
			Dish dish, final OntologyManagerService ontologyManagerService) {
		this.dish = dish;
		this.ontologyManagerService = ontologyManagerService;

		initContent();
	}

	private void initContent() {
		setHeaderTitle(dish.getId() != null ? "Edit" : "Add");
		configureContent();
		configureFooter();
/*        setWidth("300px");
        setHeight("500px");*/
		setCloseOnOutsideClick(false);
		setCloseOnEsc(true);
	}
//
	private void configureContent() {


		TextField name = new TextField("Name");
		name.setRequired(true);

		TextField ingredients = new TextField("Ingredients");
		ingredients.setRequired(true);




		binder = new Binder<>(Dish.class);


		binder.forField(name).asRequired("Name is required").bind(Dish::getName, Dish::setName)
			  .setAsRequiredEnabled(true);
//TODO fix
//		binder.forField(ingredients).bind(Dish::getIngredients, Dish::setIngredients);


		binder.readBean(dish);

		HorizontalLayout locationLayout = new HorizontalLayout();
		locationLayout.setAlignItems(FlexComponent.Alignment.BASELINE);


		FormLayout formLayout = new FormLayout( name, ingredients);
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
				binder.writeBean(dish);
				ontologyManagerService.editCreateDish(dish);
				close();
				//reloadGrid();
			} catch (ValidationException e) {
				throw new RuntimeException(e);
			}
		};
	}

	public void addSaveClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
		saveButton.addClickListener(listener);
	}


}
