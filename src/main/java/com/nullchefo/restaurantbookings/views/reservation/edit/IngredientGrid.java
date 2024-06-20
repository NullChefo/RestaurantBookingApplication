package com.nullchefo.restaurantbookings.views.reservation.edit;

import com.nullchefo.restaurantbookings.entity.Dish;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;

public class IngredientGrid extends Grid<IngredientClass> {
	public IngredientGrid() {
		super(IngredientClass.class, false);
		setSelectionMode(SelectionMode.MULTI);
		addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
		configureView();
	}

	private void configureView() {
		addColumn(IngredientClass::getId).setHeader("Id").setSortable(true).setSortable(true);
		addColumn(IngredientClass::getName).setHeader("Name").setSortable(true).setSortable(true);
		addColumn(IngredientClass::getType).setHeader("Type").setSortable(true).setSortable(true);
		setSizeFull();
	}


}
