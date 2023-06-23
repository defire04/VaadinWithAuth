package com.example.views;


import com.example.components.UserEditor;
import com.example.data.entity.User;
import com.example.data.service.UserService;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;



public class AdminView extends VerticalLayout {

    public AdminView(UserService userService, UserEditor userEditor) {
        addClassName("list-view");
        setSizeFull();


        Grid<User> grid = new Grid<>(User.class);
        grid.setAllRowsVisible(true);

        grid.setColumns("username", "name", "email", "role", "password");

        grid.setItems(userService.getAll());

        Button addNewBtn = new Button("Add new");
        HorizontalLayout toolBar = new HorizontalLayout(addNewBtn);


        Div content = new Div(grid, userEditor);
        content.addClassName("content");
        content.setSizeFull();

        add(toolBar,  content);

        grid.asSingleSelect().addValueChangeListener(e -> {
            userEditor.editUser(e.getValue());
        });

        addNewBtn.addClickListener(e -> userEditor.editUser(new User()));

        userEditor.setChangeHandler(() -> {
            userEditor.setVisible(false);
            grid.setItems(userService.getAll());
        });
    }
}