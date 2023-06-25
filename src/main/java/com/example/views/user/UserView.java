package com.example.views.user;

import com.example.components.NavigationBar;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class UserView extends VerticalLayout {
    public UserView(){
        NavigationBar navigationBar = new NavigationBar();
        add(navigationBar);

        Button edit = new Button("Edit profile", e ->{
            UI.getCurrent().getPage().setLocation("edit");
        });

        add(edit);
    }
}
