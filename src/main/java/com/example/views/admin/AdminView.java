package com.example.views.admin;


import com.example.components.AdminEditForm;
import com.example.components.NavigationBar;
import com.example.data.entity.User;
import com.example.data.service.UserService;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;



public class AdminView extends VerticalLayout {

    public AdminView(UserService userService, AdminFormEditor adminFormEditor) {

        addClassName("list-view");
        setSizeFull();

        Grid<User> grid = new Grid<>(User.class);
        grid.setAllRowsVisible(true);

        grid.setColumns("username", "name", "email", "role", "password", "mustChangePassword");
        grid.setItems(userService.getAll());

        Button addNewBtn = new Button("Add new");
        HorizontalLayout toolBar = new HorizontalLayout(addNewBtn);

        Div content = new Div(grid, adminFormEditor);
        content.addClassName("content");
        content.setSizeFull();

        add(toolBar, content);

        grid.asSingleSelect().addValueChangeListener(e -> {
            adminFormEditor.editUser(e.getValue());
        });

        AdminEditForm adminEditForm = new AdminEditForm();

        adminFormEditor.setAdminUserEditForm(adminEditForm)
                .addBindingAndValidation();


        addNewBtn.addClickListener(e -> {
            adminFormEditor.editUser(new User());
        });

        adminFormEditor.setChangeHandler(() -> {
            adminFormEditor.setVisible(false);
            grid.setItems(userService.getAll());
        });
    }
}