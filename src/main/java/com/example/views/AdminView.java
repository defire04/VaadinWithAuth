package com.example.views;

import com.example.components.RefreshGridEvent;
import com.example.components.UserEditor;
import com.example.data.entity.User;
import com.example.data.service.UserService;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;


public class AdminView extends VerticalLayout {

    public AdminView(UserService userService, UserEditor userEditor) {

        Grid<User> grid = new Grid<>(User.class);
        grid.setAllRowsVisible(true);

        grid.setColumns("username", "name", "email", "roles", "password");

        grid.setItems(userService.getAll());

        Button addNewBtn = new Button("Add new");
        HorizontalLayout toolBar = new HorizontalLayout(addNewBtn);
        add(toolBar, grid, userEditor);

        grid.setHeight("300px");


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