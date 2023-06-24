package com.example.views.user;


import com.example.components.UserEditForm;
import com.example.data.service.UserService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class EditUserView extends VerticalLayout {

    private final UserService authService;

    public EditUserView(UserService authService) {
        this.authService = authService;
        addClassName("edit-view");

        UserEditForm userEditForm = new UserEditForm();
        add(userEditForm);

        UserEditBinder userEditBinder = new UserEditBinder(userEditForm, authService);
        userEditBinder.addBindingAndValidation();


    }
}