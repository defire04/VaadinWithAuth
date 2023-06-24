package com.example.views.admin;


import com.example.components.AdminEditForm;
import com.example.data.entity.User;
import com.example.data.service.UserService;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;


public class AdminView extends VerticalLayout {

    public AdminView(UserService userService, AdminUserEditor adminUserEditor) {
        addClassName("list-view");
        setSizeFull();

        Grid<User> grid = new Grid<>(User.class);
        grid.setAllRowsVisible(true);

        grid.setColumns("username", "name", "email", "role", "password", "mustChangePassword");
        grid.setItems(userService.getAll());

        Button addNewBtn = new Button("Add new");
        HorizontalLayout toolBar = new HorizontalLayout(addNewBtn);

        Div content = new Div(grid, adminUserEditor);
        content.addClassName("content");
        content.setSizeFull();

        add(toolBar, content);

        grid.asSingleSelect().addValueChangeListener(e -> {
            adminUserEditor.editUser(e.getValue());
        });

        AdminEditForm adminEditForm = new AdminEditForm();
        adminUserEditor.setAdminUserEditForm(adminEditForm);

        AdminEditFormBinder adminEditFormBinder = new AdminEditFormBinder(adminEditForm);
        adminEditFormBinder.addBindingAndValidation();

        addNewBtn.addClickListener(e -> {
            adminUserEditor.editUser(new User());
        });

        adminUserEditor.setChangeHandler(() -> {
            adminUserEditor.setVisible(false);
            grid.setItems(userService.getAll());
        });
    }
}