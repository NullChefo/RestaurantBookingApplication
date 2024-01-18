package com.nullchefo.restaurantbookings.views.mycustomview;

import com.nullchefo.restaurantbookings.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("My Custom View")
@Route(value = "my-custom-view", layout = MainLayout.class)
@AnonymousAllowed
@Uses(Icon.class)
public class MyCustomViewView extends Composite<VerticalLayout> {

    public MyCustomViewView() {
        VerticalLayout layoutColumn2 = new VerticalLayout();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutColumn2.setWidthFull();
        getContent().setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        getContent().add(layoutColumn2);
    }
}
