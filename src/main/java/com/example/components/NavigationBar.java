package com.example.components;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.router.RouteConfiguration;

import java.util.List;

public class NavigationBar extends Div {
    public NavigationBar() {
        addClassName("navigation-bar");
        MenuBar menu = new MenuBar();
        RouteConfiguration.forSessionScope().getAvailableRoutes().forEach(routeData -> {

            menu.addItem(routeData.getTemplate(), event -> {
                UI.getCurrent().getPage().setLocation(routeData.getTemplate());
            });
        });

        add(menu);

    }
}
