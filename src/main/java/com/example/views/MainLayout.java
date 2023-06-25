package com.example.views;

import com.example.components.NavigationBar;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("home")
public class MainLayout  extends VerticalLayout {
    public MainLayout(){
        NavigationBar navigationBar = new NavigationBar();
        add(navigationBar);
    }
}
