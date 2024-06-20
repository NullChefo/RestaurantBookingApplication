package com.nullchefo.restaurantbookings.views.ontology;

import com.nullchefo.restaurantbookings.entity.Dish;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;

public class DishGrid extends Grid<Dish> {
	public DishGrid() {
		super(Dish.class, false);
		setSelectionMode(SelectionMode.MULTI);
		addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
		configureView();
	}

	private void configureView() {
		addColumn("id").setHeader("Id").setSortable(true).setSortable(true);
		addColumn("name").setHeader("Name").setSortable(true).setSortable(true);
		addColumn(dish -> String.join(", ", dish.getIngredient().getVegetables()))
				.setHeader("Vegetables");

		addColumn(dish -> String.join(", ", dish.getIngredient().getDairy()))
				.setHeader("Dairy");

		addColumn(dish -> String.join(", ", dish.getIngredient().getMeat()))
				.setHeader("Meat");

		addColumn(dish -> String.join(", ", dish.getIngredient().getNuts()))
				.setHeader("Nuts");

		addColumn(dish -> String.join(", ", dish.getIngredient().getGrain()))
				.setHeader("Grain");

		addColumn(dish -> String.join(", ", dish.getIngredient().getOthers()))
				.setHeader("Others");

		setSizeFull();
	}
}


