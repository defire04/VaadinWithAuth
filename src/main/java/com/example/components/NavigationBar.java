package com.example.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.router.RouteConfiguration;

public class NavigationBar extends Div {
    public NavigationBar() {
        addClassName("navigation-bar");
        MenuBar menu = new MenuBar();
        RouteConfiguration.forSessionScope().getAvailableRoutes().forEach(routeData -> {

            menu.addItem(routeData.getTemplate(), event -> {
                UI.getCurrent().getPage().setLocation(routeData.getTemplate());
            });
        });

        UI.getCurrent().getElement().getThemeList().add("dark");
        add(menu);
    }
}
